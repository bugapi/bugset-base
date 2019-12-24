package org.bugapi.bugset.base.util.file.excel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

/**
 * excel解析时，性别转换器
 *
 * @author zhangxw
 * @since 0.0.1
 */
public class GenderConverter implements Converter<Integer> {
    public static final String MALE = "男";
    public static final String FEMALE = "女";
    public static final String UNKNOWN_GENDER = "未知性别";

    @Override
    public Class<Integer> supportJavaTypeKey() {
        return Integer.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Integer convertToJavaData(CellData cellData, ExcelContentProperty excelContentProperty,
                                     GlobalConfiguration globalConfiguration) throws Exception {
        String stringValue = cellData.getStringValue();
        if (MALE.equals(stringValue)) {
            return 1;
        } else if (FEMALE.equals(stringValue)) {
            return 2;
        }
        return null;
    }

    @Override
    public CellData convertToExcelData(Integer integer, ExcelContentProperty excelContentProperty,
                                       GlobalConfiguration globalConfiguration) throws Exception {
        if (null == integer) {
            return new CellData(UNKNOWN_GENDER);
        } else if (1 == integer) {
            return new CellData(MALE);
        } else if (2 == integer) {
            return new CellData(FEMALE);
        }
        return new CellData(UNKNOWN_GENDER);
    }
}
