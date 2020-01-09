package com.appv1.Plan4Success;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;

import static com.appv1.Plan4Success.TabFragment1.mPreferences;


public class WordListAdapter extends
        RecyclerView.Adapter<WordListAdapter.WordViewHolder>
        implements TabFragment1.OnIntentReceived
        {

    public final LinkedList<Model> mWordList;
    private final LayoutInflater mInflater;

    private static final String Tag = "MyActivity";
    private OnItemClickListener mListener;
    public Context mContext;
    public static final int TEXT_REQUEST = 1;


    public View ItemView;
    public WordListAdapter mAdapter;



    public WordViewHolder holder;


    public String ActualReply = "";

    boolean value = false;

    //UpdateTask ut = new WordViewHolder(ItemView.getContext(), ItemView, new WordListAdapter(mContext, mWordList));
    //@Override
    public void onIntent(String reply, int position) {
        ActualReply = reply;
        value = true;
        Log.d(Tag, "MMM" + ActualReply);
        //ut.update(ActualReply);


    }


    public  interface UpdateTask{
        void update(String reply);
    }

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    class WordViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        public ImageView CheckedTextView;
        public TextView wordItemView;

        public CardView mCardView;

        public TextView prioritiesView;
        public TextView TimeManageView;



        SparseBooleanArray itemStateArray= new SparseBooleanArray();


        /**
         * Creates a new custom view holder to hold the view to display in
         * the RecyclerView.
         *
         * @param itemView The view in which to display the data.
         * @param adapter The adapter that manages the the data and views
         *                for the RecyclerView.
         */
        public WordViewHolder( Context context, View itemView,  WordListAdapter adapter) {
            super(itemView);
            wordItemView = (TextView) itemView.findViewById(R.id.AssignmentTitle);

            prioritiesView = (TextView) itemView.findViewById(R.id.priorities);
            TimeManageView = (TextView) itemView.findViewById(R.id.Time);
            CheckedTextView = (ImageView) itemView.findViewById(R.id.delete);

            //prioritiesView.setText(mWordList.size());


            //Submit = (Button)loginDialog.findViewById(R.id.Submit);
            mContext = context;

            mAdapter = adapter;


            /*wordItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), SecondActivity.class);
                    String message = wordItemView.getText().toString();
                    //intent.putExtra("task", message);
                    Log.d(Tag, "Intent Working");
                    v.getContext().startActivity(intent);

                }
            });*/
            CheckedTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //removeAt(getAdapterPosition());
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        Log.d(Tag, "answer: " + getAdapterPosition());
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onDeleteClick(getAdapterPosition());
                            //removeAt(getAdapterPosition());
                            //mWordList.remove(getAdapterPosition());
                            //notifyItemRemoved(getAdapterPosition());
                            notifyItemRangeChanged(getAdapterPosition(), mWordList.size());
                            if(position ==0){
                                SharedPreferences.Editor preferencesEditor = mPreferences.edit();
                                preferencesEditor.putInt("Size", 0);
                                preferencesEditor.apply();
                            }

                            //wordItemView.setText("Enter in Task");
                            //prioritiesView.setText(getAdapterPosition());
                            //TimeManageView.setText(0);
                        }
                    }
                }
            });



            //this.setIsRecyclable(false);

               /*         itemView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                public void onClick(View v) {
                    mWordList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(),mWordList.size());
                    Log.d(Tag, "Removed");
                    //Toast.makeText(context,"Removed : ",Toast.LENGTH_SHORT).show();
                }
            });
            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CheckedTextView.isChecked()) {
                        removeAt(getAdapterPosition());
                    }
                }
            });*/
        }

        @Override
        public void onClick(View view) {

            // Get the position of the item that was clicked.
            /*if(view.equals(itemView)) {
                /*int mPosition = getLayoutPosition();

                // Use that to access the affected item in mWordList.
                String element = mWordList.get(mPosition);
                // Change the word in the mWordList.

                mWordList.set(mPosition, "Clicked! " + element);
                // Notify the adapter, that the data has changed so it can
                // update the RecyclerView to display the data.
                mAdapter.notifyDataSetChanged();
                Intent intent =  new Intent(view.getContext(), SecondActivity.class);
                String message = wordItemView.getText().toString();
                intent.putExtra(EXTRA_MESSAGE, message);
                String message2 = prioritiesView.getText().toString();
                intent.putExtra(EXTRA_MESSAGE2, message2);
                String message3 = TimeManageView.getText().toString();
                intent.putExtra(EXTRA_MESSAGE3, message3);
                view.getContext().startActivity(intent);
                LocalBroadcastManager.getInstance(view.getContext()).sendBroadcast(intent);

                Log.d(Tag,"Working");
            }*/

            //Log.d(Tag,"Working");
            //removeAt(getAdapterPosition());
        }




    }








    public void removeAt(int position) {
        mWordList.remove(position);
        Log.d(Tag,"size: "+ mWordList.size());

        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mWordList.size());
        notifyDataSetChanged();
    }

    public WordListAdapter(Context context, LinkedList<Model> wordList) {
        mInflater = LayoutInflater.from(context);
        this.mWordList = wordList;
    }

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to
     * represent an item.
     *
     * This new ViewHolder should be constructed with a new View that can
     * represent the items of the given type. You can either create a new View
     * manually or inflate it from an XML layout file.
     *
     * The new ViewHolder will be used to display items of the adapter using
     * onBindViewHolder(ViewHolder, int, List). Since it will be reused to
     * display different items in the data set, it is a good idea to cache
     * references to sub views of the View to avoid unnecessary findViewById()
     * calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after
     *                 it is bound to an adapter position.
     * @param viewType The view type of the new View. @return A new ViewHolder
     *                 that holds a View of the given view type.
     */
    @Override
    public WordListAdapter.WordViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // Inflate an item view.
        ItemView = mInflater.inflate(
                R.layout.wordlist_item, parent, false);

        return new WordViewHolder(parent.getContext(), ItemView, this);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     * This method should update the contents of the ViewHolder.itemView to
     * reflect the item at the given position.
     *
     * @param holder   The ViewHolder which should be updated to represent
     *                 the contents of the item at the given position in the
     *                 data set.
     * @param position The position of the item within the adapter's data set.
     */

    @Override
    public void onBindViewHolder(final WordListAdapter.WordViewHolder holder, int position) {
        // Retrieve the data for that position.
        //CardView cardView = holder.mCardView;
        String mCurrent = mWordList.get(position).task;
        int mCurrent2 = mWordList.get(position).priority;

        int mCurrent3 = mWordList.get(position).time;
        Log.d(Tag, "reply123:" + mCurrent2);

        // Add the data to the view holder.
        holder.wordItemView.setText(mCurrent);
        holder.prioritiesView.setText(Integer.toString(mCurrent2));
        holder.TimeManageView.setText(Integer.toString(mCurrent3));



       holder.wordItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position2 = holder.getAdapterPosition();
                Intent intent = new Intent(mContext, SecondActivity.class);
                intent.putExtra("task", mWordList.get(position2).task);
                intent.putExtra("priority", mWordList.get(position2).priority);
                intent.putExtra("time", mWordList.get(position2).time);
                String index = "" +position2;
                intent.putExtra("index", index);
                Log.d(Tag, "Intent Working: " + position2);
                //mContext.startActivity(intent);
                ((Activity) mContext).startActivityForResult(intent, TEXT_REQUEST);
                //holder.wordItemView.setText(ActualReply);
                //onIntent(ActualReply);


            }
        });

        //holder.CheckedTextView.setImageDrawable(position);


        //holder.CheckedTextView.setImageResource(getAdapterPosition());
        //holder.prioritiesView.setText(mCurrent);
        // holder.TimeManageView.setText(mCurrent);

        //in some cases, it will prevent unwanted situations
        //holder.CheckedTextView.setOnCheckedChangeListener(null);




    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return mWordList.size();
    }




}

