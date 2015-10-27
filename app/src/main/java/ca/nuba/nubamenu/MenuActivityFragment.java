package ca.nuba.nubamenu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * A placeholder fragment containing a simple view.
 */
public class MenuActivityFragment extends Fragment{


    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String ARG_LOCATION = "ARG_LOCATION";
    public static final String ARG_MENU_TYPE = "ARG_MENU_TYPE";

    private int mPage, tabPosition;


    private View rootView;
    private String mLocation;
    private String mMenuType;
    private MenuArrayAdapter mArrayAdapter;
    String[] mPlates = {"Najibs", "Mjadara", "Flafel", "Eggplant Stew",
            "Kafta", "Kibbeh", "Beef", "Chicken", "Hushwie"};
    MenuItem[] lunchMezze, lunchPlates, lunchPitas, lunchSalads, lunchSoups, lunchToShare, lunchBeverages,
            brunchAll, brunchBevs, dinnerColdMezze, dinnerHotMezze, dinnerToShare, dinnerSoupsSalads, dinnerMains, features;
    private static TabHost tabHost;

    //int resID = getActivity().getResources().getIdentifier("ic_launcher", "mipmap", "ca.nuba.nubamenu");

    //TODO: make price float, so User can change the currency(CAD, USD etc), and in sepperate array
    //TODO: Obviously all this data will come from outside
    public static MenuActivityFragment newInstance(int page, String location, String menuType) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        args.putString(ARG_LOCATION, location);
        args.putString(ARG_MENU_TYPE, menuType);


        MenuActivityFragment fragment = new MenuActivityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
        mLocation = getArguments().getString(ARG_LOCATION);
        mMenuType = getArguments().getString(ARG_MENU_TYPE);

//        Intent intent = getActivity().getIntent();
//        if (intent != null) {
//            Bundle extras = intent.getExtras();
//            mIntentLocation = extras.getString("EXTRA_LOCATION");
//            mIntentType = extras.getString("EXTRA_TYPE");
//            mIntentPage = extras.getInt("EXTRA_PAGE",1);
//        }


        int resID = getActivity().getResources().getIdentifier("ic_launcher", "mipmap", "ca.nuba.nubamenu");

        lunchMezze = new MenuItem[]{
                new MenuItem(resID, "Mjadra", "$8.50", true, true, true, "Organic green lentils and rice with onions and jalapeño, served with avocado and caramelized onions"),
                new MenuItem(resID, "Hummus", "$7.50", true, true, true, "Organic chickpeas blended with garlic, lemon and tahini"),
                new MenuItem(resID, "Baba Ghanooj", "$8.50", true, true, true, "Creamy roasted eggplant puree with citrus and tahini "),
                new MenuItem(resID, "Najib's Special", "$8.50", true, true, true, "Crispy cauliflower tossed with lemon and sea salt, served with tahini "),
                new MenuItem(resID, "Garden Falafel", "$7.95", true, true, true, "Victor’s secret recipe of organic chickpeas, fava beans, veggies and spices"),
                new MenuItem(resID, "Chicken Tawook", "$9.50", false, false, true, "Grilled Mount Lehman chicken thigh marinated in paprika, thyme, lemon and garlic confit with hummus and avocado"),
                new MenuItem(resID, "Lamb Hashwie", "$9.50", false, false, true, "Sautéed lamb with onions, peppers, pinenuts and spices over hummus and avocado ")
        };

        lunchPlates = new MenuItem[]{
                new MenuItem(R.drawable.plate_mjadra, "Mjadra", "$11.95", true, true, true, "Organic green lentils and rice with onions and jalapeño, served with avocado and caramelized onions"),
                new MenuItem(R.drawable.plate_najibs, "Najib's Special", "$11.95", true, true, true, "Crispy cauliflower tossed with lemon and sea salt, served with tahini "),
                new MenuItem(R.drawable.plate_vstew, "Eggplant Stew", "$11.95", true, true, true, "Seasonal vegetables stewed with onions, tomatoes and chickpeas"),
                new MenuItem(R.drawable.plate_falafel, "Falafel", "$11.95", true, true, true, "Victor’s secret recipe of organic chickpeas, fava beans, veggies and spices"),
                new MenuItem(R.drawable.plate_chicken, "Chicken Tawook", "$13.50", false, false, true, "Grilled Mount Lehman chicken breast marinated in paprika, thyme, lemon and garlic confit with hummus "),
                new MenuItem(R.drawable.plate_kafta, "Lamb Kafta", "$13.75", false, false, true, "Grilled grain-fed halal lamb patty, seasoned with onions, parsley, and spices"),
                new MenuItem(R.drawable.plate_kibbe, "Lamb Kibbeh", "$13.50", false, false, false, "Grilled grain-fed halal lamb patty, vegetables, pinenuts, burghul and aromatic spices"),
                new MenuItem(R.drawable.plate_beef, "Beef Tenderloin Kebab", "$14.50", false, false, true, "Local grass-fed beef skewer. Grilled to perfection with tahini sauce")

        };

        lunchPitas = new MenuItem[]{
                new MenuItem(resID, "Mjadra", "$8.50", true, true, false, "Organic green lentils and rice with onions and jalapeño, served with avocado and caramelized onions "),
                new MenuItem(resID, "Najib's Special", "$8.50", true, true, false, "Crispy cauliflower tossed with lemon and sea salt, hummus and taboulleh "),
                new MenuItem(resID, "Falafel", "$7.00", true, true, false, "Victor’s secret recipe of organic chickpeas, fava beans, veggies and spices"),
                new MenuItem(resID, "Garden Falafel", "$8.50", true, true, false, "Victor’s secret falafel recipe with avocado and hummus"),
                new MenuItem(resID, "Veggie Fraiche", "$7.75", true, true, false, "Baba ganooj, taboulleh, red peppers, organic carrots, green onions, cucumber and avocado"),
                new MenuItem(resID, "Chicken Tawook", "$9.75", false, false, false, "Grilled mount lehman chicken breast marinated in paprika, thyme, lemon and garlic confit"),
                new MenuItem(resID, "Lamb Hushwie", "$9.75", false, false, false, "Sautéed lamb with onions, peppers, pinenuts and spices over hummus"),
                new MenuItem(resID, "Delux Toasted", "$11.75", true, false, false, "Your choice of falafel or najib with baba, Macedonian feta, tabbouleh and caramelized onions"),
                new MenuItem(resID, "Delux Toasted Veggie", "$9.75", false, false, false, "Your choice of chicken or lamb; with baba, Macedonian feta, taboulleh and caramelized onions")
        };

        lunchSalads = new MenuItem[]{
                new MenuItem(resID, "Taboulleh", "$8.25", true, true, false, "Hand chopped parsley, tomatoes, green onions and burghul in a lemon-mint dressing"),
                new MenuItem(resID, "Fattoush Salad", "$10.50", true, true, false, "Organic greens, tomato, cucumber, green onion, chickpeas and carrots with a garlic lemon-herb dressing and pita chips "),
                new MenuItem(resID, "Grilled Chicken & Feta Salad", "$13.95", false, false, true, "With organic greens, tomato, cucumber, chickpeas, carrots and green onions with a lemon-sumac dressing ")
        };

        lunchSoups = new MenuItem[]{
                new MenuItem(resID, "Daily Vegan Soup", "$4.25", true, true, true, "Always fresh"),
                new MenuItem(resID, "Red Lentil Soup", "$4.25", true, true, true, "Organic red lentils with veggies and aromatic spices")
        };

        lunchToShare = new MenuItem[]{
                new MenuItem(resID, "Le Petit Feast", "$16.00", true, true, false, "Hummus, taboulleh, baba ganooj and homemade pickle"),
                new MenuItem(resID, "La Feast", "$34.00", true, false, false, "A two course vegetarian mezze sampler including Le Petite Feast, fatoush salad, Najib’s special, falafel, mdjadra, macedonian feta, and marinated olives")
        };

        lunchBeverages = new MenuItem[]{
                new MenuItem(resID, "10 Ounce Juice", "$4.75", true, true, true, "Create your own special blend of either Orange, Lime, Apple, Beet, Ginger, Carrot, Celery or Grapefruit juice."),
                new MenuItem(resID, "14 Ounce Juice", "$6.00", true, true, true, "Create your own special blend of either Orange, Lime, Apple, Beet, Ginger, Carrot, Celery or Grapefruit juice"),
        };

        brunchAll = new MenuItem[]{
                new MenuItem(resID, "Rice Pudding", "$8.00", false, true, true, "Victor’s traditional rice and milk pudding, with a pear raisin compote "),
                new MenuItem(resID, "Healthy Wake-Up", "$8.00", false, false, false, "Create your own special blend of either Orange, Lime, Apple, Beet, Ginger, Carrot, Celery or Grapefruit juice"),
                new MenuItem(resID, "Two Eggs Any Style", "$10.00", false, false, false, "2 Eggs with Turkish bread, house made lamb bacon, roasted potatoes and fresh tomatoes"),
                new MenuItem(resID, "Two Eggs", "$7.00", false, false, false, "Same as above without lamb  bacon"),
                new MenuItem(resID, "Breakfast Manoushe", "$10.00", false, false, false, "Grilled Manoushe, poached eggs, mushrooms, peppers, feta, Merguez sausage, and arugula salad"),
                new MenuItem(resID, "Sides", "$3.00", false, false, false, "Granola, Green Salad, House Made Lamb Bacon, Roasted Potatoes, Lamb Merguez, Free-Range Egg"),
        };

        brunchBevs = new MenuItem[]{
                new MenuItem(resID, "Fresh Juice Mimosa", "$8.00", false, false, false, "Your choice of fresh orange, grapefruit or apple juice with sparkling wine"),
                new MenuItem(resID, "Nuba Carrot Caesar", "$8.50", false, true, false, "Cumin salt rim, Finlandia vodka, carrot and lemon juices, our house made hot sauce, and Braggs seasoning"),
                new MenuItem(resID, "The Hot Tease", "$7.00", false, false, false, "Choice of either: Earl grey tea with Baileys or Assam Breakfast tea with whiskey and black currant liqueur"),
                new MenuItem(resID, "Lebanese Coffee", "$8.00", false, false, false, "Fig brandy, espresso, and cardamom cream"),
                new MenuItem(resID, "Cardamom Hot Chocolate", "$3.50", false, false, false, "Italian chocolate, cocoa, cardamom, and homogenized milk"),
                new MenuItem(resID, "Orange Blossom Mocha", "$4.00", false, false, false, "Coffee, Italian chocolate, cacao, rosewater, orange blossom, and homogenized milk")
        };

        dinnerColdMezze = new MenuItem[]{
                new MenuItem(resID, "Hummus", "$7.50", true, true, true, "Organic chickpeas blended with garlic, lemon and tahini"),
                new MenuItem(resID, "Baba Ghanooj", "$8.50", true, true, true, "Creamy roasted eggplant puree with lemon and tahini"),
                new MenuItem(resID, "Marinated Olives", "$6.50", true, true, true, "Selected mixed olives marinated with thyme, lemon juice, orange zest and olive oil"),
                new MenuItem(resID, "Macedonian Feta", "$3.00", true, false, false, "")
        };
        dinnerHotMezze = new MenuItem[]{
                new MenuItem(resID, "Najib’s Special", "$8.75", true, true, true, "Crispy cauliflower tossed with lemon and sea salt, served with tahini"),
                new MenuItem(resID, "Garden Falafel", "$7.95", true, true, true, "Victor’s secret recipe of organic chickpeas, fava beans, veggies and spices with hummus and avocado"),
                new MenuItem(resID, "Mjadra", "$8.50", true, true, true, "Organic green lentils and rice with onions and jalapeño, served with avocado and caramelized onions"),
                new MenuItem(resID, "Chicken Shish Tawook", "$10.00", false, false, true, "Mount Lehman chicken breast skewers served with hummus and avocado"),
                new MenuItem(resID, "Lamb Hushwie", "$9.50", false, false, true, "Sautéed minced lamb with onions, pine nuts and spices. Served with hummus and avocado"),
                new MenuItem(resID, "Lamb Kibbeh Sainieh", "$9.50", false, false, false, "Grilled grain-fed halal lamb patty with vegetables, pine nuts, burghul and aromatic spices"),
                new MenuItem(resID, "Vegan Stew", "$8.25", true, true, true, "Stewed seasonal veggies with tomatoes, onions, and chickpeas served over brown rice"),
                new MenuItem(resID, "Halloumi Cheese", "$10.00", true, false, false, "Traditional Middle Eastern cheese. Seared and served with fresh tomato, pomegranate nut dressing, and fresh mint"),
                new MenuItem(resID, "Spiced Prawns", "$15.00", true, true, false, "Sautéed wild prawns tossed with red chermoula and chill garlic oil")
        };

        dinnerToShare = new MenuItem[]{
                new MenuItem(resID, "Le Petit Feast", "$16.00", true, true, false, "Hummus, taboulleh, baba ghanooj and homemade pickles"),
                new MenuItem(resID, "La Feast", "$35.00", true, false, false, "Two course vegetarian mezze sampler, including Le Petit Feast, Najib’s Special, falafel, mjadra, vegan stew, fattoush salad, Macedonian feta, and olives"),
                new MenuItem(resID, "Le Grand Feast", "$57.00", false, false, false, "Two course sampler, which includes Le Petit Feast, fattoush salad, Najib’s Special, roasted potatoes, lamb lollipops, prawns with chermoula sauce, and chicken skewers.")
        };
        dinnerSoupsSalads = new MenuItem[]{
                new MenuItem(resID, "Red Lentil Soup", "$4.25", true, true, true, "Organic red lentils with veggies and aromatic spices"),
                new MenuItem(resID, "Taboulleh Salad", "$8.25", true, true, false, "Hand chopped parsley, tomatoes, green onions, and burghul in a lemon-mint dressing"),
                new MenuItem(resID, "Fattoush Salad", "$10.50", true, true, true, "Organic greens, tomato, cucumber, green onion with a garlic-lemon-sumac dressing and pita chips"),
                new MenuItem(resID, "Fruit et Feta Salad", "$11.50", true, false, true, "Macedonian feta, dried nuts and figs with organic greens and a red wine pomegranate vinaigrette ")
        };
        dinnerMains = new MenuItem[]{
                new MenuItem(resID, "Stuffed Eggplant", "$19.00", true, false, true, "Roasted eggplant with a spicy tomato and red pepper stuffing, topped with feta dressing and toasted walnuts."),
                new MenuItem(resID, "Market Fish", "MP", false, false, true, "Daily prepared seasonal fish. Please ask your server for today’s feature"),
                new MenuItem(resID, "Grilled Chicken", "$23.00", false, false, true, "Fraser Valley half-chicken marinated with pepper, yoghurt, garlic, lemon, and herbs, served with batata harra and roasted seasonal vegetables"),
                new MenuItem(resID, "Beef Bavette", "$26.00", false, false, false, "Cooked in a rosemary and red wine drizzle, served with potato feta terrine, baby arugula, toasted pine nuts, fresh lemon, and crispy pita"),
                new MenuItem(resID, "Lamb Chops", "$29.00", false, false, true, "Frenched and grilled medium in a pomegranate reduction, served with hummus and monk’s salad")

        };
        features = new MenuItem[]{
                new MenuItem(resID, "Mjadra Halloumi Pita", "$10.50", true, false, false,"Mjadra pita with melted halloumi cheese","pita"),
                new MenuItem(resID, "Mediterranean Chicken Pita", "$11.50", false, false, false,"Chicken pita with tomato confit and olives","pita"),
                new MenuItem(resID, "Malfouf Plate", "$13.50", false, false, true, "Cabbage rolls with lamb and rice","plate"),
                new MenuItem(resID, "Lamb Stew Plate", "$13.50", false, false, false,"Stew made with lamb and carrots","plate"),
                new MenuItem(resID, "Lamb Stew", "$10.50", false, false, false,"Stew made with lamb and carrots on rice","hotMezze"),
                new MenuItem(resID, "Man’oushe", "$8.50", false, false, false, "Grilled Lebanese-style flatbread with daily toppings","hotMezze"),

        };

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //View rootView = inflater.inflate(R.layout.fragment_menu, container, false);
        rootView = inflater.inflate(R.layout.fragment_menu, container, false);



        ArrayList<MenuItem> arrayOfMenuItems = new ArrayList<MenuItem>();
        mArrayAdapter = new MenuArrayAdapter(getActivity(), arrayOfMenuItems);
        ListView listView = (ListView) rootView.findViewById(R.id.menu_listview);
        listView.setAdapter(mArrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MenuItem menuItemDetails = mArrayAdapter.getItem(position);

                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("picturePath", menuItemDetails.picturePath);
                intent.putExtra("name", menuItemDetails.name);
                intent.putExtra("price", menuItemDetails.price);
                intent.putExtra("v", menuItemDetails.v);
                intent.putExtra("ve", menuItemDetails.ve);
                intent.putExtra("gf", menuItemDetails.gf);
                intent.putExtra("desc", menuItemDetails.desc);
                //for up intent
                intent.putExtra("EXTRA_LOCATION", mLocation);
                intent.putExtra("EXTRA_TYPE", mMenuType);
                intent.putExtra("EXTRA_PAGE", mPage);
                //intent.putExtra("EXTRA_TAB", tabHost.getCurrentTab());

                startActivity(intent);
            }
        });
        //((ActionBarActivity)getActivity()).getSupportActionBar().setStackedBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(getActivity(), R.color.action_bar)));
//        getActivity().getSupportActionBar().setStackedBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(getActivity(), R.color.action_bar)));


        //TODO: make "Lunch" array and add there all lunch menus, and then change "switch" to loop

        switch (mMenuType){
            case "Lunch": {
                switch (mPage){
                    case 1:{
                        mArrayAdapter.addAll(lunchMezze);
                        return listView;
                    }
                    case 2:{
                        mArrayAdapter.addAll(lunchPlates);
                        return listView;
                    }
                    case 3:{
                        mArrayAdapter.addAll(lunchPitas);
                        return listView;
                    }
                    case 4:{
                        mArrayAdapter.addAll(lunchSalads);
                        return listView;
                    }
                    case 5:{
                        mArrayAdapter.addAll(lunchSoups);
                        return listView;
                    }
                    case 6:{
                        mArrayAdapter.addAll(lunchToShare);
                        return listView;
                    }
                    case 7:{
                        mArrayAdapter.addAll(lunchBeverages);
                        return listView;
                    }
                }
            }
            case "Dinner":{
                switch (mPage){
                    case 1:{
                        mArrayAdapter.addAll(dinnerColdMezze);
                        return listView;
                    }
                    case 2:{
                        mArrayAdapter.addAll(dinnerHotMezze);
                        return listView;
                    }
                    case 3:{
                        mArrayAdapter.addAll(dinnerToShare);
                        return listView;
                    }
                    case 4:{
                        mArrayAdapter.addAll(dinnerSoupsSalads);
                        return listView;
                    }
                    case 5:{
                        mArrayAdapter.addAll(dinnerMains);
                        return listView;
                    }

                }
            }
            case "Brunch":{
                switch (mPage){
                    case 1:{
                        mArrayAdapter.addAll(brunchAll);
                        return listView;
                    }
                    case 2:{
                        mArrayAdapter.addAll(brunchBevs);
                        return listView;
                    }

                }
            }
        }









        return rootView;
    }





    public View menuFilter(){


        ArrayList<MenuItem> arrayOfUsers = new ArrayList<MenuItem>();
        mArrayAdapter = new MenuArrayAdapter(getActivity(), arrayOfUsers);
        ListView listView = (ListView) rootView.findViewById(R.id.menu_listview);
        listView.setAdapter(mArrayAdapter);


        //TODO: make "Lunch" array and add there all lunch menus, and then change "switch" to loop

        switch (mMenuType){
            case "Lunch": {
                switch (mPage){
                    case 1:{
                        for (int i = 0; i<lunchMezze.length; i++){

                        }
                        mArrayAdapter.addAll(lunchMezze);
                        return listView;
                    }
                    case 2:{
                        mArrayAdapter.addAll(lunchPlates);
                        return listView;
                    }
                    case 3:{
                        mArrayAdapter.addAll(lunchPitas);
                        return listView;
                    }
                    case 4:{
                        mArrayAdapter.addAll(lunchSalads);
                        return listView;
                    }
                    case 5:{
                        mArrayAdapter.addAll(lunchSoups);
                        return listView;
                    }
                    case 6:{
                        mArrayAdapter.addAll(lunchToShare);
                        return listView;
                    }
                    case 7:{
                        mArrayAdapter.addAll(lunchBeverages);
                        return listView;
                    }
                }
            }
            case "Dinner":{
                switch (mPage){
                    case 1:{
                        mArrayAdapter.addAll(dinnerColdMezze);
                        return listView;
                    }
                    case 2:{
                        mArrayAdapter.addAll(dinnerHotMezze);
                        return listView;
                    }
                    case 3:{
                        mArrayAdapter.addAll(dinnerToShare);
                        return listView;
                    }
                    case 4:{
                        mArrayAdapter.addAll(dinnerSoupsSalads);
                        return listView;
                    }
                    case 5:{
                        mArrayAdapter.addAll(dinnerMains);
                        return listView;
                    }

                }
            }
            case "Brunch":{
                switch (mPage){
                    case 1:{
                        mArrayAdapter.addAll(brunchAll);
                        return listView;
                    }
                    case 2:{
                        mArrayAdapter.addAll(brunchBevs);
                        return listView;
                    }

                }
            }
        }

        return rootView;
    }



}
