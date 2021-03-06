package com.liujun.search.engine.collect.operation.docraw.docrawFind;

import com.liujun.search.algorithm.boyerMoore.CommCharMatcherInstance;
import com.liujun.search.common.io.ByteUtils;
import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.collect.constant.DocRawFindEnum;

import java.util.List;

/**
 * 数据转换，将数据转换为完整的网页信息
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/24
 */
public class DataConvert implements FlowServiceInf {

  public static final DataConvert INSTANCE = new DataConvert();

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    // 取出数据存储集合对象
    List<byte[]> dataList = context.getObject(DocRawFindEnum.PROC_COLLECT_OUT_DATA.getKey());

    // 获取一个完整的byte数组
    byte[] outDataBytes = ByteUtils.BytesConvert(dataList);

    String outdataContext = getDataContext(outDataBytes);
    context.put(DocRawFindEnum.OUT_FIND_DATA_CONTEXT.getKey(), outdataContext);
    context.put(DocRawFindEnum.OUT_FIND_END_FLAG.getKey(), true);

    return false;
  }

  /**
   * 获取数据内容信息
   *
   * @param dataVale 数据内容信息
   * @return 数据内容
   */
  public String getDataContext(byte[] dataVale) {

    int startIndex = 0;

    // 查找得到网页的id
    startIndex = CommCharMatcherInstance.LINE_COLUMN_MATCHER.matcherIndex(dataVale, startIndex);
    // 得到长度
    startIndex = CommCharMatcherInstance.LINE_COLUMN_MATCHER.matcherIndex(dataVale, startIndex + 1);
    startIndex = startIndex + 1;
    // 返回最终的数据集
    return new String(dataVale, startIndex, dataVale.length - startIndex);
  }
}
