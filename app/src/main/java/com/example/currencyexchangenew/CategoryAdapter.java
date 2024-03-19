package com.example.currencyexchangenew;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    Context context;
    List<Category> categories;

    public CategoryAdapter(Context context, List<Category> categories) {
        this.context = context;  //страница на которой все должно быть выведено
        this.categories = categories;  // список категорий которые должны быть выведены
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View categoryItems = LayoutInflater.from(context).inflate(R.layout.category_item,parent,false);  //указываем дизайн
        return new CategoryViewHolder(categoryItems);      // указываем элементы с которыми будем работать, они указываются в отдельном влож классе
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {    //создаем объект на основе вложеного класса
        holder.category_text.setText(categories.get(position).getTitle());            // через объект обращаемся к нужным полям(category_text) и устан текс для этого поля

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static final class CategoryViewHolder extends RecyclerView.ViewHolder {

        TextView category_text;    //вложеный класс

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            category_text = itemView.findViewById(R.id.category_text);  //ссылка на нужный обьект из дизайна
        }
    }
}
