package com.seindev.sehalsein.restaurantmanagement;

import android.view.View;

/**
 * Created by sehalsein on 20/02/16.
 */
public interface CashierClickListner {
    public void itemClicked(View view, String BillId, int Sno, String TableNo, float TotalAmount);
}
