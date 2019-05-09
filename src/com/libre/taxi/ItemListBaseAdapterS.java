package com.libre.taxi;
import java.util.ArrayList;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
public class ItemListBaseAdapterS extends BaseAdapter {
 private static ArrayList<String> itemDetailsrrayList;
  
 private Integer[] imgid = {
   R.drawable.avatarm,
   R.drawable.avatarm,
   R.drawable.avatarm,
   R.drawable.avatarm,
   R.drawable.avatarm,
   R.drawable.avatarm
   };
  
 private LayoutInflater l_Inflater;
 
 public ItemListBaseAdapterS(Context context, ArrayList<String> result) {
  itemDetailsrrayList = result;
  l_Inflater = LayoutInflater.from(context);
 }
 
 public ItemListBaseAdapterS(SeleccionarTaxiActivity context,
		ArrayList<String> result) {
	// TODO Auto-generated constructor stub
}

public int getCount() {
  return itemDetailsrrayList.size();
 }
 
 public Object getItem(int position) {
  return itemDetailsrrayList.get(position);
 }
 
 public long getItemId(int position) {
  return position;
 }
 
 public View getView(int position, View convertView, ViewGroup parent) {
  ViewHolder holder;
  if (convertView == null) {
   convertView = l_Inflater.inflate(R.layout.row, null);
   holder = new ViewHolder();
   holder.txt_itemName = (TextView) convertView.findViewById(R.id.textView);
   //holder.txt_itemDescription = (TextView) convertView.findViewById(R.id.conductorTelefono);
   //holder.txt_itemPrice = (TextView) convertView.findViewById(R.id.conductorfecha);
   holder.itemImage = (ImageView) convertView.findViewById(R.id.imageView);
   /*
    *
    * holder.txt_itemName = (TextView) convertView.findViewById(R.id.name);
   holder.txt_itemDescription = (TextView) convertView.findViewById(R.id.itemDescription);
   holder.txt_itemPrice = (TextView) convertView.findViewById(R.id.price);
   holder.itemImage = (ImageView) convertView.findViewById(R.id.photo);
    * */
 
   convertView.setTag(holder);
  } else {
   holder = (ViewHolder) convertView.getTag();
  }
   
  //holder.txt_itemName.setText(itemDetailsrrayList.get(position).getConductor());
  //holder.txt_itemDescription.setText(itemDetailsrrayList.get(position).getTelefono());
  //holder.txt_itemPrice.setText(itemDetailsrrayList.get(position).getFecha());
 // holder.itemImage.setImageResource(imgid[itemDetailsrrayList.get(position).getImageNumber() - 1]);
 
  return convertView;
 }
 
 static class ViewHolder {
  TextView txt_itemName;
  TextView txt_itemDescription;
  TextView txt_itemPrice;
  ImageView itemImage;
 }
}