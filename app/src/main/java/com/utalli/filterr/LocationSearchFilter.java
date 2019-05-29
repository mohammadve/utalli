/*
package com.utalli.filterr;

import android.content.Context;
import android.widget.Filter;
import com.utalli.activity.SearchActivity;
import com.utalli.adapter.SearchPlaceAdapter;

import java.util.ArrayList;

public class LocationSearchFilter extends Filter {
    ArrayList<String> stringArrayList;
    Context context;


    public LocationSearchFilter(Context context, ArrayList<String> stringArrayList){
        this.stringArrayList = stringArrayList;
        this.context = context;

    }



    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        // TODO Auto-generated method stub

        constraint = constraint.toString().toLowerCase();

        FilterResults newFilterResults = new FilterResults();

        if (constraint != null && constraint.length() > 0) {


            ArrayList<String> auxData = new ArrayList<String>();

            for (int i = 0; i < stringArrayList.size(); i++) {
                if (stringArrayList.get(i).toLowerCase().contains(constraint))
                    auxData.add(stringArrayList.get(i));
            }

            newFilterResults.count = auxData.size();
            newFilterResults.values = auxData;
        } else {

            newFilterResults.count = stringArrayList.size();
            newFilterResults.values = stringArrayList;
        }

        return newFilterResults;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        ArrayList<String> resultData = new ArrayList<String>();

        resultData = (ArrayList<String>) results.values;

        SearchPlaceAdapter adapter = new SearchPlaceAdapter(context, resultData);
        new SearchActivity().rv_languageList.setAdapter(adapter);

//              notifyDataSetChanged();
    }





}
*/
