package com.postgres.model;

import com.postgres.manager.MetaFieldAnnotation;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class UserBase {
    /**
     * ID
     */
    @ApiModelProperty(value = "id", example = "-1")
    @MetaFieldAnnotation(fieldCode = "id_1")
    private int id = -1;

    /**
     * 获取基础模型声明的字段(含所有父类的字段)
     *
     * @return 字段列表
     */
    protected List<Field> getBaseModelDeclaredFields() {
        List<Field> fields = new ArrayList<>();
        fields.addAll(Arrays.asList(this.getClass().getDeclaredFields()));
        getSuperClassField(fields, this.getClass());
        return fields;
    }

    /**
     * 获取父类声明的字段列表
     *
     * @param fields       字段列表
     * @param currentClass 当前类
     */
    private void getSuperClassField(List<Field> fields, Class<?> currentClass) {
        Class<?> superClass = currentClass.getSuperclass();
        if (superClass != null) {
            fields.addAll(Arrays.asList(superClass.getDeclaredFields()));
            getSuperClassField(fields, superClass);
        }
    }

    /**
     * 将字段属性转换为元数据列表
     *
     * @return 元数据列表
     */
    public Map<String, Object> toMetaDataList() {
        Map<String, Object> dataMap = new HashMap<>();
        List<Field> fields = getBaseModelDeclaredFields();
        for (Field field : fields) {
            MetaFieldAnnotation annotation = field.getDeclaredAnnotation(MetaFieldAnnotation.class);
            try {
                field.setAccessible(true);
                dataMap.put(annotation.fieldCode(), field.get(this));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return dataMap;
    }

    /**
     * 根据元数据列表填充属性值
     *
     * @param dataMap 元数据列表
     */
    public void fillByMetaData(Map<String, Object> dataMap) {
        List<Field> fields = getBaseModelDeclaredFields();
        for (Field field : fields) {
            MetaFieldAnnotation annotation = field.getDeclaredAnnotation(MetaFieldAnnotation.class);
            if (annotation != null && dataMap.containsKey(annotation.fieldCode())) {
                try {
                    field.setAccessible(true);
                    field.set(this, dataMap.get(annotation.fieldCode()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
