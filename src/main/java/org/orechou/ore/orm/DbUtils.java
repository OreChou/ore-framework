package org.orechou.ore.orm;

import org.orechou.ore.annotation.orm.Id;
import org.orechou.ore.annotation.orm.Size;
import org.orechou.ore.test.entity.User;
import org.orechou.ore.utils.NamingStringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 数据库的工具类
 *
 * @author OreChou
 * @create 2017-12-21 19:31
 */
public class DbUtils {

    private static TypeConvertor typeConvertor;

    static {
        typeConvertor = new MysqlTypeConvertor();
    }

    /**
     * 根据传入的类类型，生成相应创建表的SQL
     * @param clazz
     * @return
     */
    public static String createTableSQL(Class<?> clazz) {
        StringBuffer sqlBuffer = new StringBuffer();
        // 将类名转换成表名，下划线的形式
        String tableName = NamingStringUtils.camelToUnderline(clazz.getSimpleName());
        sqlBuffer.append("create table " + tableName + " ");
        List<Field> fields = new ArrayList<>(Arrays.asList(clazz.getDeclaredFields()));
        // 找到并生成ID字段
        Field idField = null;
        String idFieldName = null;
        for (Field field : fields) {
            if (field.isAnnotationPresent(Id.class)) {
                idFieldName = NamingStringUtils.camelToUnderline(field.getName());
                String fieldType = typeConvertor.javaTypeToDatabaseType(field.getType().getSimpleName());
                sqlBuffer.append(String.format("(%s %s not null, ", idFieldName, fieldType));
                idField = field;
            }
        }
        fields.remove(idField);
        // 生成其余剩余的字段
        for (int i = 0; i < fields.size(); i++) {
            Field field = fields.get(i);
            String fieldName = NamingStringUtils.camelToUnderline(field.getName());
            String fieldType = typeConvertor.javaTypeToDatabaseType(field.getType().getSimpleName());
            // 若为varchar，则需要查看指定的大小
            if (fieldType.equals("varchar")) {
                // 检查是否又Size字段，若没有则设置默认为32位
                if (field.isAnnotationPresent(Size.class)) {
                    Size size = field.getAnnotation(Size.class);
                    sqlBuffer.append(String.format("%s %s(%s), ", fieldName, fieldType, size.value()));
                } else {
                    sqlBuffer.append(String.format("%s %s(31), ", fieldName, fieldType));
                }
            } else {
                sqlBuffer.append(String.format("%s %s, ", fieldName, fieldType));
            }
        }
        // 生成主键
        sqlBuffer.append(String.format("primary key (%s))", idFieldName));
        return sqlBuffer.toString();
    }

    public static void main(String[] args) {
        System.out.println(createTableSQL(User.class));
    }

}
