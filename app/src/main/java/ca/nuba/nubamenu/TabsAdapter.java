package ca.nuba.nubamenu;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsAdapter extends FragmentPagerAdapter {

    private String tabLunchTitles[] = new String[] { "Mezzes", "Lunch Plates", "Pitas", "Salads","Soups", "To Share", "Beverages"};
    private String tabDinnerTitles[] = new String[] {"Cold Mezzes", "Hot Mezzes","To Share", "Soups, Salads & More","Mains"};
    private String tabBrunchTitles[] = new String[] {"Brunch","SIGNATURE BRUNCH BEVERAGES"};
    final int L_PAGE_COUNT = tabLunchTitles.length;
    final int D_PAGE_COUNT = tabDinnerTitles.length;
    final int B_PAGE_COUNT = tabBrunchTitles.length;
    private Context context;
    private String location;
    private String menuType;

    public TabsAdapter(FragmentManager fm, Context context, String location, String menuType) {
        super(fm);
        this.context = context;
        this.location = location;
        this.menuType = menuType;
    }

    @Override
    public int getCount() {
        switch (menuType){
            case "Lunch":{
                return L_PAGE_COUNT;
            }
            case "Dinner":{
                return D_PAGE_COUNT;
            }
            case "Brunch":{
                return B_PAGE_COUNT;
            }
            default: {
                return 0;
            }
        }
    }

    @Override
    public Fragment getItem(int position) {
        return MenuActivityFragment.newInstance(position + 1, location, menuType);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (menuType.equals("Lunch")){
            return tabLunchTitles[position];
        } else if (menuType.equals("Dinner")){
            return tabDinnerTitles[position];
        } else {
            return tabBrunchTitles[position];
        }
    }

    //TODO: if small number of tabs - take whole screen (in getTabView)
}