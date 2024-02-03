package kz.qBots.qSoft.data.component;

import kz.qBots.qSoft.data.entity.SubCategory;

public interface SubCategoryComponent {
    SubCategory findById(int id);
    void setEnable(boolean enable,int id);
}
