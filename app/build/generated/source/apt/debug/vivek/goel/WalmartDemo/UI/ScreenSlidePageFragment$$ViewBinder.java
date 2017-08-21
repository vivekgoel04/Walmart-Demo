// Generated code from Butter Knife. Do not modify!
package vivek.goel.WalmartDemo.UI;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ScreenSlidePageFragment$$ViewBinder<T extends vivek.goel.WalmartDemo.UI.ScreenSlidePageFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558538, "field 'ivImage'");
    target.ivImage = finder.castView(view, 2131558538, "field 'ivImage'");
    view = finder.findRequiredView(source, 2131558537, "field 'tvTitle'");
    target.tvTitle = finder.castView(view, 2131558537, "field 'tvTitle'");
    view = finder.findRequiredView(source, 2131558540, "field 'tvPrice'");
    target.tvPrice = finder.castView(view, 2131558540, "field 'tvPrice'");
    view = finder.findRequiredView(source, 2131558542, "field 'tvOverview'");
    target.tvOverview = finder.castView(view, 2131558542, "field 'tvOverview'");
    view = finder.findRequiredView(source, 2131558544, "field 'tvSize'");
    target.tvSize = finder.castView(view, 2131558544, "field 'tvSize'");
    view = finder.findRequiredView(source, 2131558546, "field 'tvColor'");
    target.tvColor = finder.castView(view, 2131558546, "field 'tvColor'");
    view = finder.findRequiredView(source, 2131558548, "field 'tvSellerInfo'");
    target.tvSellerInfo = finder.castView(view, 2131558548, "field 'tvSellerInfo'");
    view = finder.findRequiredView(source, 2131558550, "field 'tvStock'");
    target.tvStock = finder.castView(view, 2131558550, "field 'tvStock'");
  }

  @Override public void unbind(T target) {
    target.ivImage = null;
    target.tvTitle = null;
    target.tvPrice = null;
    target.tvOverview = null;
    target.tvSize = null;
    target.tvColor = null;
    target.tvSellerInfo = null;
    target.tvStock = null;
  }
}
