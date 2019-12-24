package org.bugapi.bugset.base.util.web;

import org.bugapi.bugset.base.constant.CharsetType;
import org.bugapi.bugset.base.util.charset.CharsetUtil;

import javax.servlet.http.HttpServletResponse;

/**
 * @author zhangxw
 * @since 0.0.1
 */
public class ResponseUtil {
    //发送响应流方法 文件后缀

    /**
     * 设置excel文件(文件名包含中文)下载的相应头信息
     *
     * @param response 相应
     * @param fileName 文件内容
     * @param fileSuffix 文件后缀
     */
    public static void setExcelFileResponseHeader(HttpServletResponse response, String fileName, String fileSuffix) {
        String downloadFileName = CharsetUtil.convert(fileName, CharsetType.GBK, CharsetType.ISO_8859_1) + fileSuffix;
        response.setContentType("application/vnd.ms-excel;charset=GBK");
        response.setHeader("Content-Disposition", "attachment;filename=" + downloadFileName);
        response.addHeader("Pargam", "no-cache");
        response.addHeader("Cache-Control", "no-cache");
    }
}
