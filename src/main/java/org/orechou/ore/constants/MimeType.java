package org.orechou.ore.constants;

/**
 * MIME的类型
 *
 * @Author OreChou
 * @Create 2017-12-10 10:34
 */
public interface MimeType {

    /**
     * 用于json提交的数据
     */
    String JSON = "application/json";

    /**
     * 使用HTTP的POST方法提交的表单
     */
    String SIMPLE_FORM = "application/x-www-form-urlencoded";

    /**
     * 同上，但主要用于表单提交时伴随文件上传的场合
     */
    String FILE_FORM = "multipart/form-data";

}
