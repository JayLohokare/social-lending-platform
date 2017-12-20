package in.skylinelabs.yhacks;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

public class LendAdapter extends ArrayAdapter<LendDetails> {


    public static String t;

    // declaring our ArrayList of items
    private List<LendDetails> objects;

    /* here we must override the constructor for ArrayAdapter
    * the only variable we care about now is ArrayList<Item> objects,
    * final Cone
    * because it is the list of objects we want to display.
    */

    final Context context = this.getContext();

    public LendAdapter(Context context, int textViewResourceId, List<LendDetails> objects) {
        super(context, textViewResourceId, objects);
        this.objects = objects;
    }

    /*
     * we are overriding the getView method here - this is what defines how each
     * list item will look.
     */
    public View getView(int position, View convertView, ViewGroup parent){


        // assign the view we are converting to a local variable
        View v = convertView;

        // first check to see if the view is null. if so, we have to inflate it.
        // to inflate it basically means to render, or show, the view.
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.lend_list_element, parent, false);

        }
        final LendDetails i = objects.get(position);



        if (i != null) {

            // This is how you obtain a reference to the TextViews.
            // These TextViews are created in the XML files we defined.

            TextView name = (TextView) v.findViewById(R.id.name);
            name.setText(i.get_name());

            TextView amount = (TextView) v.findViewById(R.id.amount);
            amount.setText(Integer.toString(i.get_amount()));

            RatingBar ratings = (RatingBar) v.findViewById(R.id.rating);
            ratings.setRating(i.get_rating());



        }
       /* v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }); */

        // the view must be returned to our activity
        return v;

    }

}


