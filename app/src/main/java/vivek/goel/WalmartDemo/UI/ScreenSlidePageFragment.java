/*
 * Copyright 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package vivek.goel.WalmartDemo.UI;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import butterknife.Bind;
import butterknife.ButterKnife;
import vivek.goel.WalmartDemo.R;
import vivek.goel.WalmartDemo.models.DataModel.Items;

public class ScreenSlidePageFragment extends Fragment {
    private static int itemNumber;
    private static ArrayList<Items> result;

    @Bind(R.id.ivImage)
    ImageView ivImage;
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.tvPrice)
    TextView tvPrice;
    @Bind(R.id.tvOverview)
    TextView tvOverview;
    @Bind(R.id.tvSize)
    TextView tvSize;
    @Bind(R.id.tvColor)
    TextView tvColor;
    @Bind(R.id.tvSellerInfo)
    TextView tvSellerInfo;
    @Bind(R.id.tvStock)
    TextView tvStock;

    public static ScreenSlidePageFragment create(int number, ArrayList<Items> data) {
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        result = data;
        itemNumber = number;
        Bundle args = new Bundle();
        args.putInt("position", number);
        fragment.setArguments(args);
        return fragment;
    }

    public ScreenSlidePageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemNumber = getArguments().getInt("position");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout containing a title and body text.
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.detail_fragment, container, false);
        ButterKnife.bind(this, rootView);
        setData(rootView);

        return rootView;
    }

    public void setData(View view) {
        if (getItemNumber() > -1) {
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            tvTitle.setText(result.get(getItemNumber()).getName());

            ivImage = (ImageView) view.findViewById(R.id.ivImage);
            Glide.with(this).load(result.get(getItemNumber()).getLargeImage()).into(ivImage);

            tvPrice = (TextView) view.findViewById(R.id.tvPrice);
            tvPrice.setText(Float.toString(result.get(getItemNumber()).getSalePrice()));

            tvOverview = (TextView) view.findViewById(R.id.tvOverview);
            tvOverview.setText(result.get(getItemNumber()).getShortDescription());

            tvSize = (TextView) view.findViewById(R.id.tvSize);
            tvSize.setText(result.get(getItemNumber()).getSize());

            tvColor = (TextView) view.findViewById(R.id.tvColor);
            tvColor.setText(result.get(getItemNumber()).getColor());

            tvSellerInfo = (TextView) view.findViewById(R.id.tvSellerInfo);
            tvSellerInfo.setText(result.get(getItemNumber()).getSellerInfo());

            tvStock = (TextView) view.findViewById(R.id.tvStock);
            tvStock.setText(result.get(getItemNumber()).getStock());
        }
    }

    /**
     * Returns the page number represented by this fragment object.
     */
    public int getItemNumber() {
        return itemNumber;
    }
}
