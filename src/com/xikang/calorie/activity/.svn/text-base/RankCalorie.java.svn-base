/* ==================================================
 * 产品名: 卡路里管理
 * 文件名: RankCalorie.java
 * --------------------------------------------------
 * 开发环境: JDK1.6
 * --------------------------------------------------
 * 修订履历  YYYY/MM/DD  REV.  备注
 *         2011/06/13  1.00  初版发行
 * --------------------------------------------------
 * (C) Copyright XIKANG CORPORATION 2011 All Rights Reserved.
 */
package com.xikang.calorie.activity;

import android.app.Activity;
import android.os.Bundle;

import com.xikang.calorie.common.Util;
/**
 * 
 * 卡路里排行榜页面
 * 
 * 
 * <pre>
 * 
 * 2011/06/15, 代俊义
 * 
 * 修订履历：2011/06/15 代俊义   添加页面实现
 * 
 * </pre>
 * 
 * @author 代俊义
 * @version 1.00
 */
public class RankCalorie extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_rank_calorie);
        Util.initRankChartView(RankCalorie.this,0);
        Util.setTitleBarTitle(this, getString(R.string.rank_county_label));
        Util.returnPage(this);
    }
}