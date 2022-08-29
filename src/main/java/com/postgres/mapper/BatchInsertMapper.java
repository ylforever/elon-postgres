package com.postgres.mapper;

import com.postgres.manager.SchemaInterceptAnnotation;
import com.postgres.model.BatchData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
@SchemaInterceptAnnotation(schemaType = "business")
public interface BatchInsertMapper {
    /**
     * 插入单条数据
     *
     * @param data PO数据
     */
    void insertData(@Param("data") BatchData data);

    /**
     * 插入数据列表
     *
     * @param dataList 数据列表
     */
    void insertDataList(@Param("list") List<BatchData> dataList);
}
