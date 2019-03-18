/*package edu.freshfutures.seeka.adapters;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import edu.freshfutures.seeka.interfaces.EntryItem;
import edu.freshfutures.seeka.interfaces.Item;
import edu.freshfutures.seeka.R;
import edu.freshfutures.seeka.SectionItem;


public class EntryAdapter extends ArrayAdapter<Item> {

    @SuppressWarnings("unused")
    private Context context;
    private ArrayList<Item> items;
    private LayoutInflater vi;

    public EntryAdapter(Context ctx, ArrayList<Item> itm) {
        super(ctx, 0, itm);
        // TODO Auto-generated constructor stub

        this.context = ctx;
        this.items = itm;
        vi = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        final Item i = items.get(position);

        if(i != null) {
            if(i.isSection()) {
                SectionItem si = (SectionItem)i;
                v = vi.inflate(R.layout.list_item_section, null);

                v.setOnClickListener(null);
                v.setLongClickable(false);
                v.setOnLongClickListener(null);

                final TextView sectionView = (TextView)v.findViewById(R.id.list_section_view);

                if(si.getTitle().equals("")) {

                    sectionView.setVisibility(View.GONE);
                    LinearLayout.LayoutParams secparams = (LinearLayout.LayoutParams)sectionView.getLayoutParams();

                    secparams.height = (int)context.getResources().getDimension(R.dimen.menu_title_height_no_text);
                    sectionView.setLayoutParams(secparams);
                    sectionView.setPadding(0,(int) context.getResources().getDimension(R.dimen.menu_title_text_size_no_text),
                            0,(int) context.getResources().getDimension(R.dimen.menu_title_text_size_no_text));

                }

                else {

                    sectionView.setText(si.getTitle());
                }

            }

            else {
                EntryItem ei = (EntryItem)i;
                v = vi.inflate(R.layout.list_settings_structure, null);
                final ImageView menuImg = (ImageView)v.findViewById(R.id.img_settings_title);
                final TextView values = (TextView) v.findViewById(R.id.text_settings_value);
                final TextView figures = (TextView) v.findViewById(R.id.figureValues);
                View bottomLine = v.findViewById(R.id.menu_bottom_line);
                final int sdk_version = Build.VERSION.SDK_INT;

                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)figures.getLayoutParams();

                //values.setTextSize((int) context.getResources().getDimension(R.dimen.settings_value_size));
                //figures.setTextSize(context.getResources().getDimension(R.dimen.figures_text_size));


                //figures.setGravity(RelativeLayout.CENTER_VERTICAL);
                //figures.setGravity(RelativeLayout.ALIGN_RIGHT);


                if(ei.value.equals("My Credits")) {

                    figures.setTextColor(Color.parseColor("#00aff0"));

                }


                if(ei.value.equals("Notifications")) {

                    figures.setPadding((int) context.getResources().getDimension(R.dimen.menu_value_padding_left),
                            (int) context.getResources().getDimension(R.dimen.menu_title_padding_top),
                            (int) context.getResources().getDimension(R.dimen.menu_value_padding_left),
                            (int) context.getResources().getDimension(R.dimen.menu_title_padding_top));

                    if(Build.VERSION.SDK_INT > 21) {
                        figures.setBackground(context.getResources().getDrawable(R.drawable.menu_figures_background, null));
                    }
                    else {
                        figures.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.menu_figures_background));
                    }

                    bottomLine.setVisibility(View.GONE);
                }

                if(ei.value.equals("Need Help")) {
                    bottomLine.setVisibility(View.GONE);
                }


                if(ei.value.equals("Purchase History")) {
                    bottomLine.setVisibility(View.GONE);
                }

                if(ei.value.equals("Currency Converter")) {
                    bottomLine.setVisibility(View.GONE);
                }

                if(ei.value.equals("Logout")) {
                    bottomLine.setVisibility(View.GONE);
                }

                if(ei.figures.equals("")) {
                    figures.setVisibility(View.GONE);
                }

                if(menuImg != null)
                    menuImg.setImageResource(ei.id);
                    values.setText(ei.value);
                    figures.setText(ei.figures);
            }
        }
        return v;

    }



}
*/