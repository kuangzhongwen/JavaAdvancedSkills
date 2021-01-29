package io.kzw.advance.main;

import java.util.HashSet;
import java.util.Set;

public class Clean {

    private static final String DATA = "\n" +
            "import com.wuba.actionlog.client.ActionLogUtils;\n" +
            "\n" +
            "import com.wuba.activity.BaseFragmentActivity;\n" +
            "\n" +
            "import com.wuba.commons.grant.PermissionsManager;\n" +
            "\n" +
            "import com.wuba.commons.grant.PermissionsManager;\n" +
            "import com.wuba.commons.network.NetUtils;\n" +
            "import com.wuba.lib.transfer.PageTransferManager;\n" +
            "import com.wuba.rx.RxDataManager;\n" +
            "import com.wuba.rx.utils.SubscriberAdapter;\n" +
            "import com.wuba.views.WubaDialog;\n" +
            "\n" +
            "import com.wuba.commons.log.LogUtil;\n" +
            "import com.wuba.commons.utils.ToastUtils;\n" +
            "import com.wuba.lib.transfer.PageTransferManager;\n" +
            "\n" +
            "\n" +
            "import com.wuba.actionlog.client.ActionLogUtils;\n" +
            "import com.wuba.commons.network.NetUtils;\n" +
            "import com.wuba.commons.utils.ThreadPoolManager;\n" +
            "import com.wuba.lib.transfer.PageTransferManager;\n" +
            "\n" +
            "import com.wuba.rx.RxDataManager;\n" +
            "import com.wuba.rx.bus.RxBus;\n" +
            "import com.wuba.rx.utils.SubscriberAdapter;\n" +
            "import com.wuba.views.WubaDialog;\n" +
            "import com.wuba.walle.ext.login.LoginPreferenceUtils;\n" +
            "\n" +
            "import com.wuba.actionlog.client.ActionLogUtils;\n" +
            "import com.wuba.commons.picture.fresco.widget.WubaDraweeView;\n" +
            "\n" +
            "import com.wuba.actionlog.client.ActionLogUtils;\n" +
            "import com.wuba.baseui.WubaHandler;\n" +
            "import com.wuba.commons.network.NetUtils;\n" +
            "import com.wuba.commons.picture.fresco.widget.WubaDraweeView;\n" +
            "import com.wuba.commons.utils.ThreadPoolManager;\n" +
            "\n" +
            "\n" +
            "import com.wuba.rx.RxDataManager;\n" +
            "import com.wuba.views.WubaDialog;\n" +
            "import com.wuba.walle.ext.login.LoginPreferenceUtils;\n" +
            "\n" +
            "\n" +
            "import com.wuba.commons.entity.BaseType;\n" +
            "\n" +
            "import com.wuba.commons.utils.UrlUtils;\n" +
            "import com.wuba.rx.RxDataManager;\n" +
            "\n" +
            "import com.wuba.commons.network.parser.AbstractParser;\n" +
            "\n" +
            "import com.facebook.drawee.backends.pipeline.Fresco;\n" +
            "import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;\n" +
            "import com.facebook.imagepipeline.common.ImageDecodeOptions;\n" +
            "import com.facebook.imagepipeline.common.ImageDecodeOptionsBuilder;\n" +
            "import com.facebook.imagepipeline.request.ImageRequest;\n" +
            "import com.facebook.imagepipeline.request.ImageRequestBuilder;\n" +
            "import com.wuba.commons.picture.fresco.widget.WubaDraweeView;\n" +
            "\n" +
            "\n" +
            "import com.wuba.commons.picture.fresco.widget.WubaDraweeView;\n" +
            "\n" +
            "import com.wuba.activity.BaseFragmentActivity;\n" +
            "import com.wuba.commons.grant.PermissionsManager;\n" +
            "\n" +
            "import com.wuba.actionlog.client.ActionLogUtils;\n" +
            "import com.wuba.activity.BaseActivity;\n" +
            "import com.wuba.baseui.TitlebarHolder;\n" +
            "import com.wuba.commons.crash.CatchUICrashManager;\n" +
            "import com.wuba.commons.log.LOGGER;\n" +
            "import com.wuba.commons.network.NetUtils;\n" +
            "import com.wuba.lib.transfer.PageTransferManager;\n" +
            "\n" +
            "import com.wuba.activity.BaseFragmentActivity;\n" +
            "\n" +
            "import com.wuba.actionlog.client.ActionLogUtils;\n" +
            "\n" +
            "import com.wuba.actionlog.client.ActionLogUtils;\n" +
            "import com.wuba.commons.file.FileUtils;\n" +
            "import com.wuba.lib.transfer.JumpEntity;\n" +
            "import com.wuba.lib.transfer.PageTransferManager;\n" +
            "import com.wuba.views.WubaDialog;\n" +
            "\n" +
            "\n" +
            "import com.wuba.commons.entity.Resp;\n" +
            "\n" +
            "import com.wuba.commons.entity.Resp;\n" +
            "import com.wuba.commons.utils.StringUtils;\n" +
            "import com.wuba.rx.RxDataManager;\n" +
            "\n" +
            "import com.wuba.commons.grant.PermissionsManager;\n" +
            "import com.wuba.commons.grant.PermissionsResultAction;\n" +
            "import com.wuba.commons.log.LOGGER;\n" +
            "import com.wuba.grant.PermissionsDialog;\n" +
            "import com.wuba.views.WubaDialog;\n" +
            "\n" +
            "\n" +
            "import com.wuba.commons.picture.fresco.utils.UriUtil;\n" +
            "import com.wuba.commons.picture.fresco.widget.WubaDraweeView;\n" +
            "import com.wuba.commons.utils.ToastUtils;\n" +
            "import com.wuba.lib.transfer.PageTransferManager;\n" +
            "import com.wuba.rx.utils.RxWubaSubsriber;\n" +
            "import com.wuba.walle.ext.login.LoginPreferenceUtils;\n" +
            "import com.wuba.walle.ext.share.ShareUtils;\n" +
            "\n" +
            "import com.wuba.views.RequestLoadingWeb;\n" +
            "\n" +
            "import com.facebook.drawee.backends.pipeline.Fresco;\n" +
            "import com.facebook.drawee.controller.AbstractDraweeController;\n" +
            "import com.facebook.imagepipeline.postprocessors.IterativeBoxBlurPostProcessor;\n" +
            "import com.facebook.imagepipeline.request.ImageRequest;\n" +
            "import com.facebook.imagepipeline.request.ImageRequestBuilder;\n" +
            "\n" +
            "import com.wuba.actionlog.client.ActionLogUtils;\n" +
            "import com.wuba.activity.BaseFragmentActivity;\n" +
            "import com.wuba.commons.file.FileUtils;\n" +
            "import com.wuba.commons.log.LOGGER;\n" +
            "import com.wuba.commons.picture.fresco.utils.UriUtil;\n" +
            "import com.wuba.commons.picture.fresco.widget.WubaDraweeView;\n" +
            "import com.wuba.commons.sysextention.WubaHandler;\n" +
            "import com.wuba.rx.utils.RxUtils;\n" +
            "import com.wuba.views.WubaDialog;\n" +
            "\n" +
            "\n" +
            "import com.wuba.actionlog.client.ActionLogUtils;\n" +
            "import com.wuba.activity.BaseFragmentActivity;\n" +
            "import com.wuba.commons.file.FileUtils;\n" +
            "import com.wuba.commons.sysextention.WubaHandler;\n" +
            "import com.wuba.lib.transfer.JumpEntity;\n" +
            "import com.wuba.lib.transfer.PageTransferManager;\n" +
            "import com.wuba.rx.utils.RxUtils;\n" +
            "import com.wuba.views.WubaDialog;\n" +
            "\n" +
            "import com.wuba.walle.ext.share.ShareUtils;\n" +
            "\n" +
            "import com.wuba.commons.crash.CatchUICrashManager;\n" +
            "import com.wuba.commons.network.NetUtils;\n" +
            "\n" +
            "import com.wuba.actionlog.client.ActionLogUtils;\n" +
            "import com.wuba.commons.AppEnv;\n" +
            "import com.wuba.commons.utils.StringUtils;\n" +
            "\n" +
            "\n" +
            "import com.wuba.commons.utils.PrivatePreferencesConstants;\n" +
            "import com.wuba.rx.RxDataManager;\n" +
            "\n" +
            "import com.wuba.commons.log.LOGGER;\n" +
            "\n" +
            "import com.wuba.commons.entity.Resp;\n" +
            "import com.wuba.commons.picture.fresco.widget.WubaDraweeView;\n" +
            "\n" +
            "import com.wuba.commons.deviceinfo.DeviceInfoUtils;\n" +
            "import com.wuba.commons.log.LOGGER;\n" +
            "\n" +
            "import com.wuba.commons.picture.fresco.widget.WubaDraweeView;\n" +
            "\n" +
            "import com.wuba.views.WubaDialog;\n" +
            "\n" +
            "import com.wuba.commons.WubaSettingCommon;\n" +
            "import com.wuba.commons.log.LOGGER;\n" +
            "import com.wuba.commons.network.NetUtils;\n" +
            "import com.wuba.commons.picture.fresco.widget.WubaSimpleDraweeView;\n" +
            "import com.wuba.commons.sysextention.WubaHandler;\n" +
            "\n" +
            "import com.wuba.commons.log.LOGGER;\n" +
            "import com.wuba.rx.RxDataManager;\n" +
            "\n" +
            "\n" +
            "import com.wuba.rx.utils.RxUtils;\n" +
            "import com.wuba.rx.utils.SubscriberAdapter;\n" +
            "\n" +
            "\n" +
            "import com.wuba.commons.log.LOGGER;\n" +
            "import com.wuba.rx.RxDataManager;\n" +
            "\n" +
            "import com.wuba.commons.log.LOGGER;\n" +
            "\n";

    public static void main(String[] args) {
        String[] splits = DATA.split(";");
        Set<String> set = new HashSet<>(splits.length);
        for (int i = 0, len = splits.length; i < len; ++i) {
            set.add(splits[i]);
        }
        for (String s : set) {
            System.out.println(s);
        }
    }
}
