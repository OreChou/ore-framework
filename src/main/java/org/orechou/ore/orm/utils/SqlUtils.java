package org.orechou.ore.orm.utils;

import org.orechou.ore.annotation.orm.Id;
import org.orechou.ore.annotation.orm.Size;
import org.orechou.ore.orm.MysqlTypeConvertor;
import org.orechou.ore.orm.TypeConvertor;
import org.orechou.ore.test.entity.User;
import org.orechou.ore.utils.NamingStringUtils;
import org.orechou.ore.utils.ReflectionUtil;

import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 数据库的工具类
 *
 * @author OreChou
 * @create 2017-12-21 19:31
 */
public class SqlUtils {

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

    /**
     *
     * @param object
     * @return
     */
    public static String insertRecordSQL(Object object) {
        StringBuffer sqlBuffer = new StringBuffer();
        int valueCount = 0;
        Class<?> clazz = object.getClass();
        List<Field> fields = new ArrayList<>(Arrays.asList(clazz.getDeclaredFields()));
        String tableName = NamingStringUtils.camelToUnderline(object.getClass().getSimpleName());
        sqlBuffer.append("insert into " + tableName + "(");
        for (int i = 0; i < fields.size(); i++) {
            Field field = fields.get(i);
            // 对象中有值得才生成对应的SQL
            if (ReflectionUtil.getField(object, field) != null) {
                String columnName = NamingStringUtils.camelToUnderline(field.getName());
                sqlBuffer.append(columnName + ", ");
                valueCount++;
            }
        }
        sqlBuffer.delete(sqlBuffer.length() - 2, sqlBuffer.length()).append(") values(");
        for (int i = 0; i < valueCount; i++) {
            if (i == valueCount - 1) {
                sqlBuffer.append("?)");
            }  else {
                sqlBuffer.append("?, ");
            }
        }
        return sqlBuffer.toString();
    }

    public static String updateRecordSQL(Object object) {
        StringBuffer sqlBuffer = new StringBuffer();
        String tableName = NamingStringUtils.camelToUnderline(object.getClass().getSimpleName());
        sqlBuffer.append("update " + tableName + " set ");
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        Field idField = null;
        for (Field field : fields) {
            Object value = ReflectionUtil.getField(object, field);
            if (value != null) {
                if (!field.isAnnotationPresent(Id.class)) {
                    sqlBuffer.append(NamingStringUtils.camelToUnderline(field.getName()) + " = ?, ");
                } else {
                    idField = field;
                }
            }
        }
        sqlBuffer.delete(sqlBuffer.length() - 2, sqlBuffer.length());
        sqlBuffer.append(" where " + NamingStringUtils.camelToUnderline(idField.getName()) + " = ?");
        return sqlBuffer.toString();
    }

    public static String queryRecordSQL(Object object) {
        StringBuffer sqlBuffer = new StringBuffer();
        String tableName = NamingStringUtils.camelToUnderline(object.getClass().getSimpleName());
        sqlBuffer.append("select * from " + tableName + " where ");
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Object value = ReflectionUtil.getField(object, field);
            if (value != null) {
                sqlBuffer.append(NamingStringUtils.camelToUnderline(field.getName()) + " = ?, ");
            }
        }
        sqlBuffer.delete(sqlBuffer.length() - 2, sqlBuffer.length());
        return sqlBuffer.toString();
    }

    public static void main(String[] args) {
        System.out.println(createTableSQL(User.class));
        User user = new User();
        user.setId((long) 123);
        user.setCreateTime(new Date());
        System.out.println(insertRecordSQL(user));
        System.out.println(updateRecordSQL(user));
        System.out.println(queryRecordSQL(user));
    }

}
