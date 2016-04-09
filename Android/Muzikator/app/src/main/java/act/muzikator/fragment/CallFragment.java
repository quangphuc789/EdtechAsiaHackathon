/*
 *  Copyright 2015 The WebRTC Project Authors. All rights reserved.
 *
 *  Use of this source code is governed by a BSD-style license
 *  that can be found in the LICENSE file in the root of the source
 *  tree. An additional intellectual property rights grant can be found
 *  in the file PATENTS.  All contributing project authors may
 *  be found in the AUTHORS file in the root of the source tree.
 */

package act.muzikator.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import org.webrtc.RendererCommon.ScalingType;

import act.muzikator.R;
import act.muzikator.activity.CallActivity;

/**
 * Fragment for call control.
 */
public class CallFragment extends Fragment {
  private View controlView;
  private ImageButton disconnectButton;
  private ImageButton pianoButton;
  private RelativeLayout virtualInstrumentContainer;
  private OnCallEvents callEvents;
  private ScalingType scalingType;
  private boolean videoCallEnabled = true;
  private boolean isDisplayingPiano = false;

  /**
   * Call control interface for container activity.
   */
  public interface OnCallEvents {
    public void onCallHangUp();
    public void onCameraSwitch();
    public void onVideoScalingSwitch(ScalingType scalingType);
    public void onCaptureFormatChange(int width, int height, int framerate);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    controlView =
        inflater.inflate(R.layout.fragment_call_2, container, false);

    // Create UI controls.
    disconnectButton =
        (ImageButton) controlView.findViewById(R.id.button_call_disconnect);

    // Add buttons click events.
    disconnectButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        callEvents.onCallHangUp();
      }
    });

    // Create UI controls.
    this.pianoButton =
            (ImageButton) controlView.findViewById(R.id.button_toggle_piano);

    // Add buttons click events.
    pianoButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if(virtualInstrumentContainer.isShown()) {
          virtualInstrumentContainer.setVisibility(View.INVISIBLE);
        } else {
          virtualInstrumentContainer.setVisibility(View.VISIBLE);
        }
      }
    });

//    // Create UI controls.
    this.virtualInstrumentContainer =
            (RelativeLayout) controlView.findViewById(R.id.virtual_instrument_container);
    if(this.virtualInstrumentContainer.isShown()) {
      this.virtualInstrumentContainer.setVisibility(View.INVISIBLE);
    }

    scalingType = ScalingType.SCALE_ASPECT_FILL;

    return controlView;
  }

  @Override
  public void onStart() {
    super.onStart();

    boolean captureSliderEnabled = false;
    Bundle args = getArguments();
    if (args != null) {
      videoCallEnabled = args.getBoolean(CallActivity.EXTRA_VIDEO_CALL, true);
      captureSliderEnabled = videoCallEnabled
          && args.getBoolean(CallActivity.EXTRA_VIDEO_CAPTUREQUALITYSLIDER_ENABLED, false);
    }
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    callEvents = (OnCallEvents) activity;
  }

}
