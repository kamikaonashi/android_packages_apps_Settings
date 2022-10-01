/*
 * Copyright (C) 2020 Wave-OS
 * Copyright (C) 2022 Project Arcana
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.settings.deviceinfo.firmwareversion;

import android.content.Context;
import android.os.Build;
import android.os.SystemProperties;
import android.widget.TextView;

import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.widget.LayoutPreference;

public class CumInfoPreferenceController extends AbstractPreferenceController {

    private static final String KEY_CUM_INFO = "cum_info";

    private static final String PROP_CUM_VERSION = "ro.cum.version";
    private static final String PROP_CUM_RELEASETYPE = "ro.cum.build_type";
    private static final String PROP_CUM_MAINTAINER = "ro.cum.maintainer";
    private static final String PROP_CUM_DEVICE = "ro.cum.device";

    public CumInfoPreferenceController(Context context) {
        super(context);
    }

    private String getDeviceName() {
        String device = SystemProperties.get(PROP_CUM_DEVICE, "");
        if (device.equals("")) {
            device = Build.MANUFACTURER + " " + Build.MODEL;
        }
        return device;
    }

    private String getCumVersion() {
        final String version = SystemProperties.get(PROP_CUM_VERSION,
                this.mContext.getString(R.string.device_info_default));

        return version + " ";
    }

    private String getCumReleaseType() {
        final String releaseType = SystemProperties.get(PROP_CUM_RELEASETYPE,
                this.mContext.getString(R.string.device_info_default));

        return releaseType.substring(0, 1).toUpperCase() +
                 releaseType.substring(1).toLowerCase();
    }

    @Override
    public void displayPreference(PreferenceScreen screen) {
        super.displayPreference(screen);
        final LayoutPreference CumInfoPreference = screen.findPreference(KEY_CUM_INFO);
        final TextView version = (TextView) CumInfoPreference.findViewById(R.id.version_message);
        final TextView device = (TextView) CumInfoPreference.findViewById(R.id.device_message);
        final TextView releaseType = (TextView) CumInfoPreference.findViewById(R.id.release_type_message);
        final TextView maintainer = (TextView) CumInfoPreference.findViewById(R.id.maintainer_message);
        final String CumVersion = getCumVersion();
        final String CumDevice = getDeviceName();
        final String CumReleaseType = getCumReleaseType();
        final String CumMaintainer = SystemProperties.get(PROP_CUM_MAINTAINER,
                this.mContext.getString(R.string.device_info_default));
        version.setText(CumVersion);
        device.setText(CumDevice);
        releaseType.setText(CumReleaseType);
        maintainer.setText(CumMaintainer);
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public String getPreferenceKey() {
        return KEY_CUM_INFO;
    }
}
