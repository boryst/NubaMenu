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
        //return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        return MenuActivityFragment.newInstance(position + 1, location, menuType);
    }

    @Override
    public CharSequence getPageTitle(int position) {

        // somehow change layout for each position
        // Generate title based on item position
        if (menuType.equals("Lunch")){
            return tabLunchTitles[position];
        } else if (menuType.equals("Dinner")){
            return tabDinnerTitles[position];
        } else {
            return tabBrunchTitles[position];
        }
    }

    //TODO: if small number of tabs - take whole screen (in getTabView)
    //TODO: change colors for app, change logo to word only, make it appear on all screes woth text

//    public View getTabView(int position) {
//        // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
//        switch (position) {
//            case 0:{
//
//
//                View v = LayoutInflater.from(context).inflate(R.layout.tab_view, null);
//
//                ArrayList<MenuItem> arrayOfItems = new ArrayList<MenuItem>();
//// Create the adapter to convert the array to views
//                MenuArrayAdapter adapter = new MenuArrayAdapter(context, arrayOfItems);
//// Attach the adapter to a ListView
//                ListView listView = (ListView)v.findViewById(R.id.tab_listview);
//                listView.setAdapter(adapter);
//
//                int resID = context.getResources().getIdentifier("ic_launcher", "mipmap", "ca.nuba.nubamenu");
//                MenuItem[] newUsers = new MenuItem[]{
//                        new MenuItem(resID, "Najibs", "$25"),
//                        new MenuItem(resID, "Najibs", "$25")};
//                adapter.addAll(newUsers);
//                return v;
//                //break;
//            }
//            case 1:{
//                View v = LayoutInflater.from(context).inflate(R.layout.tab_view, null);
//                return v;
////                break;
//                }
//            case 2:{
//                View v = LayoutInflater.from(context).inflate(R.layout.tab_view, null);
//                return v;
////                break;
//            }
//            case 3:{
//                View v = LayoutInflater.from(context).inflate(R.layout.tab_view, null);
//                return v;
////                break;
//            }
//            case 4:{
//                View v = LayoutInflater.from(context).inflate(R.layout.tab_view, null);
//                return v;
////                break;
//            }
//            case 5:{
//                View v = LayoutInflater.from(context).inflate(R.layout.tab_view, null);
//                return v;
////                break;
//            }
//            case 6:{
//                View v = LayoutInflater.from(context).inflate(R.layout.tab_view, null);
//                return v;
////                break;
//            }
//            default:{
//                View v = LayoutInflater.from(context).inflate(R.layout.tab_view, null);
//                return v;
//            }
//        }
//
////        View v = LayoutInflater.from(context).inflate(R.layout.fragment_menu, null);
//        //extView tv = (TextView) v.findViewById(R.id.textView);
//        //tv.setText(tabTitles[position]);
//        //ImageView img = (ImageView) v.findViewById(R.id.imgView);
//        //img.setImageResource(imageResId[position]);
//        //return v;
//    }

//    public View getTabView(int position) {
//        // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
//        View v = LayoutInflater.from(context).inflate(R.layout.tab_view, null);
//        TextView tv = (TextView) v.findViewById(R.id.tab_text);
//        tv.setText(tabTitles[position]);
//        ImageView img = (ImageView) v.findViewById(R.id.tab_img);
//        img.setImageResource(R.mipmap.ic_launcher);
//        return v;
//    }
}