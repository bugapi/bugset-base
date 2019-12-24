package org.bugapi.bugset.base.util.file.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.metadata.WriteSheet;
import org.bugapi.bugset.base.util.array.ArrayUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author zhangxw
 * 参考地址：https://alibaba-easyexcel.github.io/quickstart/write.html
 * @since 0.0.1
 */
public class ExcelUtil {

    /**
     * 读取Excel文件的指定sheet页面
     *
     * @param filePath  excel文件路径
     * @param sheetNo   sheet页面的编号【如果为null，默认去读取sheetNo为0的sheet页面】
     * @param clazz     要转成的Java对象的Class对象
     * @param consumer  java8的消费型函数，参数为对象List
     * @param threshold 批量处理的阈值
     * @param <T>       泛型类型
     */
    public <T> void readExcelBySheetNo(String filePath, Integer sheetNo, Class<T> clazz, Consumer<List<T>> consumer,
                                       int threshold) {
        // 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(filePath, clazz, getExcelAnalysisListener(consumer, threshold)).sheet(sheetNo).doRead();
    }

    /**
     * 读取Excel文件的指定sheet页面
     *
     * @param filePath  excel文件路径
     * @param sheetName sheet页面的名字【如果为null，默认去读取sheetNo为0的sheet页面】
     * @param clazz     要转成的Java对象的Class对象
     * @param consumer  java8的消费型函数，参数为对象List
     * @param threshold 批量处理的阈值
     * @param <T>       泛型类型
     */
    public <T> void readExcelBySheetName(String filePath, String sheetName, Class<T> clazz, Consumer<List<T>> consumer,
                                         int threshold) {
        // 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(filePath, clazz, getExcelAnalysisListener(consumer, threshold)).sheet(sheetName).doRead();
    }

    /**
     * 读取Excel文件的所有sheet页面
     *
     * @param filePath  excel文件路径
     * @param clazz     要转成的Java对象的Class对象
     * @param consumer  java8的消费型函数，参数为对象List
     * @param threshold 批量处理的阈值
     * @param <T>       泛型类型
     */
    public <T> void readExcelAllSheet(String filePath, Class<T> clazz, Consumer<List<T>> consumer, int threshold) {
        // 这里需要注意 DemoDataListener的doAfterAllAnalysed 会在每个sheet读取完毕后调用一次。然后所有sheet都会往同一个DemoDataListener里面写
        EasyExcel.read(filePath, clazz, getExcelAnalysisListener(consumer, threshold)).doReadAll();
    }

    /**
     * 读取Excel文件的指定的多个sheet页面
     *
     * @param filePath  excel文件路径
     * @param sheetNos  sheet页面编号数组
     * @param clazz     要转成的Java对象的Class对象
     * @param consumer  java8的消费型函数，参数为对象List
     * @param threshold 批量处理的阈值
     * @param <T>       泛型类型
     */
    public <T> void readExcelBySheetNos(String filePath, int[] sheetNos, Class<T> clazz,
                                        Consumer<List<T>> consumer, int threshold) {
        readExcelBySheetNosOrSheetNames(filePath, sheetNos, null, clazz, consumer, threshold);
    }

    /**
     * 读取Excel文件的指定的多个sheet页面
     *
     * @param filePath   excel文件路径
     * @param sheetNames sheet页面名称数组
     * @param clazz      要转成的Java对象的Class对象
     * @param consumer   java8的消费型函数，参数为对象List
     * @param threshold  批量处理的阈值
     * @param <T>        泛型类型
     */
    public <T> void readExcelBySheetNames(String filePath, String[] sheetNames, Class<T> clazz,
                                          Consumer<List<T>> consumer, int threshold) {
        readExcelBySheetNosOrSheetNames(filePath, null, sheetNames, clazz, consumer, threshold);
    }

    /**
     * 读取Excel文件的指定的多个sheet页面
     *
     * @param filePath   excel文件路径
     * @param sheetNos   sheet页面编号数组
     * @param sheetNames sheet页面名称数组
     * @param clazz      要转成的Java对象的Class对象
     * @param consumer   java8的消费型函数，参数为对象List
     * @param threshold  批量处理的阈值
     * @param <T>        泛型类型
     */
    private <T> void readExcelBySheetNosOrSheetNames(String filePath, int[] sheetNos, String[] sheetNames,
                                                     Class<T> clazz, Consumer<List<T>> consumer, int threshold) {
        ExcelReader excelReader = EasyExcel.read(filePath).build();
        // 获取Excel解析监听器
        AnalysisEventListener<T> analysisEventListener = getExcelAnalysisListener(consumer, threshold);

        List<ReadSheet> readSheetList = constructReadSheetList(sheetNos, sheetNames, clazz, analysisEventListener);
        // 这里注意 一定要把多个sheet一起传进去，不然有个问题就是03版的excel 会读取多次，浪费性能
        excelReader.read(readSheetList);
        // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
        excelReader.finish();
    }

    /**
     * 获取可读的sheet页面集合
     *
     * @param sheetNos              sheet页面编号数组
     * @param sheetNames            sheet页面名称数组
     * @param clazz                 要转成的Java对象的Class对象
     * @param analysisEventListener Excel解析监听器
     * @param <T>                   泛型类型
     * @return List<ReadSheet> 需要读的sheet的列表
     */
    private <T> List<ReadSheet> constructReadSheetList(int[] sheetNos, String[] sheetNames,
                                                       Class<T> clazz, AnalysisEventListener<T> analysisEventListener) {
        sheetNos = ArrayUtil.removeRepeatData(sheetNos);
        List<ReadSheet> readSheetList = new ArrayList<>();
        if (sheetNos.length > 0) {
            for (int sheetNo : sheetNos) {
                readSheetList.add(EasyExcel.readSheet(sheetNo).head(clazz).registerReadListener(analysisEventListener).build());
            }
        } else {
            sheetNames = ArrayUtil.removeRepeatData(sheetNames);
            if (sheetNames.length > 0) {
                for (String sheetName : sheetNames) {
                    readSheetList.add(EasyExcel.readSheet(sheetName).head(clazz).registerReadListener(analysisEventListener).build());
                }
            }
        }
        return readSheetList;
    }

    /**
     * 将列表中的数据写入到excel文件的sheet页面
     *
     * @param filePath  excel文件路径
     * @param sheetName sheet页面的名字
     * @param clazz     要转成的Java对象的Class对象
     * @param data      要写入的数据集合
     * @param <T>       泛型类型
     */
    public <T> void writeExcelBySheetName(String filePath, String sheetName, Class<T> clazz, List<T> data) {
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(filePath, clazz).sheet(sheetName).doWrite(data);
    }


    public <T> void writeExcel(Class<T> clazz, String filePath, String sheetName, List<T> data) {

        // 写法2
        // 这里 需要指定写用哪个class去写
/*        ExcelWriter excelWriter = EasyExcel.write(filePath, clazz).build();
        WriteSheet writeSheet = EasyExcel.writerSheet(sheetName).build();
        excelWriter.write(data, writeSheet);
        /// 千万别忘记finish 会帮忙关闭流
        excelWriter.finish();*/


        // 方法1 如果写到同一个sheet
        // 这里 需要指定写用哪个class去写
        ExcelWriter excelWriter = EasyExcel.write(filePath, clazz).build();
        // 这里注意 如果同一个sheet只要创建一次
        WriteSheet writeSheet = EasyExcel.writerSheet("模板").build();
        // 去调用写入,这里我调用了五次，实际使用时根据数据库分页的总的页数来
        for (int i = 0; i < 5; i++) {
            // 分页去数据库查询数据 这里可以去数据库查询每一页的数据
            excelWriter.write(data, writeSheet);
        }
        /// 千万别忘记finish 会帮忙关闭流
        excelWriter.finish();

        // 方法2 如果写到不同的sheet 同一个对象
        // 这里 指定文件
        excelWriter = EasyExcel.write(filePath, clazz).build();
        // 去调用写入,这里我调用了五次，实际使用时根据数据库分页的总的页数来。这里最终会写到5个sheet里面
        for (int i = 0; i < 5; i++) {
            // 每次都要创建writeSheet 这里注意必须指定sheetNo
            writeSheet = EasyExcel.writerSheet(i, "模板").build();
            // 分页去数据库查询数据 这里可以去数据库查询每一页的数据
            excelWriter.write(data, writeSheet);
        }
        /// 千万别忘记finish 会帮忙关闭流
        excelWriter.finish();

        // 方法3 如果写到不同的sheet 不同的对象
        // 这里 指定文件
        excelWriter = EasyExcel.write(filePath).build();
        // 去调用写入,这里我调用了五次，实际使用时根据数据库分页的总的页数来。这里最终会写到5个sheet里面
        for (int i = 0; i < 5; i++) {
            // 每次都要创建writeSheet 这里注意必须指定sheetNo。这里注意DemoData.class 可以每次都变，我这里为了方便 所以用的同一个class 实际上可以一直变
            writeSheet = EasyExcel.writerSheet(i, "模板").head(clazz).build();
            // 分页去数据库查询数据 这里可以去数据库查询每一页的数据
            excelWriter.write(data, writeSheet);
        }
        /// 千万别忘记finish 会帮忙关闭流
        excelWriter.finish();
    }

    /**
     * 获取excel解析事件的监听器
     *
     * @param consumer  消费型函数
     * @param threshold 批量处理的阈值
     * @param <T>       泛型类型
     * @return excel解析事件的监听器
     */
    public static <T> AnalysisEventListener<T> getExcelAnalysisListener(Consumer<List<T>> consumer, int threshold) {
        return new AnalysisEventListener<T>() {
            private LinkedList<T> linkedList = new LinkedList<>();

            /**
             * 解析到一行数据
             *
             * @param t 从excel中解析到的一行数据对应的对象
             * @param analysisContext excel解析的上下文
             */
            @Override
            public void invoke(T t, AnalysisContext analysisContext) {
                linkedList.add(t);
                if (linkedList.size() >= threshold) {
                    consumer.accept(linkedList);
                    linkedList.clear();
                }
            }

            /**
             * 数据解析完毕
             * @param analysisContext excel解析的上下文
             */
            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                if (linkedList.size() > 0) {
                    consumer.accept(linkedList);
                }
            }
        };
    }
}
