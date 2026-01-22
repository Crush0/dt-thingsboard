package org.thingsboard.server.common.data.goview;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ResultTable {
     /**
     * 状态码
     * */
    private Integer code;

    /**
     * 提示消息
     * */
    private String msg;

    /**
     * 消息总量
     * */
    private Long count;

    /**
     * 数据对象
     * */
    private Object data;

    /**
     * 构 建
     * */
    public static ResultTable pageTable(long count,Object data){
        ResultTable resultTable = new ResultTableBuilder().code(0).msg("success").count(count).data(data).build();
        if(data!=null) {
       	 resultTable.setMsg("获取成功");
       }else {
       	 resultTable.setMsg("获取失败");
       }
        return resultTable;
    }

    public static ResultTable dataTable(Object data){
        ResultTable resultTable = new ResultTableBuilder().code(0).msg("success").data(data).build();
        if(data!=null) {
        	 resultTable.setMsg("获取成功");
        }else {
        	 resultTable.setMsg("获取失败");
        }
       
        return resultTable;
    }
}