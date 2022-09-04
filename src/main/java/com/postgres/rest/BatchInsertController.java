package com.postgres.rest;

import com.elon.base.util.ListUtil;
import com.postgres.mapper.BatchInsertMapper;
import com.postgres.model.BatchData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/batch-insert")
@Api(tags = "批量插入数据性能测试")
public class BatchInsertController {
    private static final String columnData = "12hskdhksdh2129192jkjksdjsjdlsjdlsjdlsjdlsjdlsd";

    @Resource
    private BatchInsertMapper batchInsertMapper;

    @Resource
    private SqlSessionFactory sqlSessionFactory;

    @PostMapping("/mybatis_batch_insert/{amount}")
    @ApiOperation(value = "mybatis 批量插入")
    public long mybatisBatchInsert(@PathVariable("amount") int amount) {
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        long beginTime = System.currentTimeMillis();

        try {
            BatchInsertMapper insertMapper = session.getMapper(BatchInsertMapper.class);

            List<BatchData> dataList = buildDataList(amount);
            for (BatchData data : dataList) {
                insertMapper.insertData(data);
            }

            session.commit();
            session.clearCache();
        } catch (Exception e) {
            session.rollback();
        } finally {
            session.close();
        }

        return System.currentTimeMillis() - beginTime;
    }

    @PostMapping("/foreach_batch_insert/{amount}")
    @ApiOperation(value = "foreach 批量插入")
    public long foreachBatchInsert(@PathVariable("amount") int amount) {
        long beginTime = System.currentTimeMillis();
        List<BatchData> dataList = buildDataList(amount);

        // 大数据分批处理入库
        List<List<BatchData>> dataGroup = ListUtil.splitList(dataList, 2000000);
        for (List<BatchData> group : dataGroup) {
            batchInsertMapper.insertDataList(group);
        }

        return System.currentTimeMillis() - beginTime;
    }

    private List<BatchData> buildDataList(int amount) {
        List<BatchData> dataList = new ArrayList<>();
        for (int i = 0; i < amount; ++i) {
            BatchData data = new BatchData();
            data.setColumn1(columnData);
            data.setColumn2(columnData);
            data.setColumn3(columnData);
            data.setColumn4(columnData);
            data.setColumn5(columnData);
            data.setColumn6(columnData);
            data.setColumn7(columnData);
            data.setColumn8(columnData);
            data.setColumn9(columnData);
            data.setColumn10(columnData);
            dataList.add(data);
        }

        return dataList;
    }
}
