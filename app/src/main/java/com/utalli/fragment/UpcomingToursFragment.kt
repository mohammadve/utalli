    package com.utalli.fragment

    import android.os.Bundle
    import android.util.Log
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import androidx.fragment.app.Fragment
    import androidx.lifecycle.Observer
    import androidx.lifecycle.ViewModelProviders
    import androidx.recyclerview.widget.LinearLayoutManager
    import com.google.gson.Gson
    import com.google.gson.JsonArray
    import com.google.gson.reflect.TypeToken
    import com.utalli.R
    import com.utalli.adapter.UpcomingTourAdapter
    import com.utalli.callBack.UpcomingTourCancelCallBack
    import com.utalli.helpers.Utils
    import com.utalli.models.UpcomingTourListModel
    import com.utalli.viewModels.MyToursViewModel
    import kotlinx.android.synthetic.main.fragment_upcoming_tours.*

    class UpcomingToursFragment : Fragment(){

        var myToursViewModel : MyToursViewModel?= null
        var upcomingTourAdapter : UpcomingTourAdapter ? = null



        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

            val view = inflater.inflate(R.layout.fragment_upcoming_tours, container, false)
            myToursViewModel = ViewModelProviders.of(activity!!).get(MyToursViewModel::class.java)
            getUpcomingToursData()
            return view
        }



        override fun setUserVisibleHint(isVisibleToUser: Boolean) {
            super.setUserVisibleHint(isVisibleToUser)
            if (isVisibleToUser) {
                //getUpcomingToursData()
            } else {
                Log.e("@@"," UpcomingToursFragment  Gone")
            }
        }
        override fun onActivityCreated(savedInstanceState: Bundle?) {
            super.onActivityCreated(savedInstanceState)
               rv_my_tours.setHasFixedSize(true)
               rv_my_tours.layoutManager = LinearLayoutManager(activity)
        }

        private fun getUpcomingToursData() {

            myToursViewModel!!.getUpcomigTours(activity!!, 1).observe(this, Observer {

                if(it != null && it.has("status") && it.get("status").asString.equals("1")) {

                    if(it.has("data") && it.get("data") is JsonArray){

                        val type = object : TypeToken<List<UpcomingTourListModel>>() {}.type
                        var upComingTourList = Gson().fromJson<List<UpcomingTourListModel>>(it.get("data"), type)

                        if(upComingTourList != null && upComingTourList.size >0){

                            rv_my_tours.visibility = View.VISIBLE
                            cl_no_upcomingTourFound.visibility = View.GONE

                            upcomingTourAdapter = UpcomingTourAdapter(activity!!, upComingTourList, object : UpcomingTourCancelCallBack{

                                override fun upComingTourListener(itemDetails: UpcomingTourListModel) {

                                    setTourCancelRequest(itemDetails.tourReqId)
                                }
                            })
                            rv_my_tours.adapter = upcomingTourAdapter
                        }
                        else
                        {
                            cl_no_upcomingTourFound.visibility = View.VISIBLE
                            rv_my_tours.visibility = View.GONE
                        }
                    }
                    else {
                        if (it.has("message"))
                            Utils.showToast(activity!!, it.get("message").asString)
                        else {
                            Utils.showToast(activity!!, getString(R.string.msg_common_error))
                        }
                    }
                }
            })
        }

        private fun setTourCancelRequest(tourReqId: Int) {

            myToursViewModel!!.cancleUpcomingTour(activity!! , tourReqId).observe(this, Observer {

                if(it != null && it.has("status") && it.get("status").asString.equals("1")) {

                    Utils.showToast(activity!!, it.get("message").asString)

                    upcomingTourAdapter!!.notifyDataSetChanged()
                }
            })
        }
    }