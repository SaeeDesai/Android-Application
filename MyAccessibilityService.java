package com.example.mychat;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

public class MyAccessibilityService extends AccessibilityService {

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        // Check if the event type is TYPE_WINDOW_STATE_CHANGED
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            // Check if the current app is WhatsApp
            if (event.getPackageName().equals("com.whatsapp")) {
                // Find the send button using the resource id
                AccessibilityNodeInfo rootNode = getRootInActiveWindow();
                if (rootNode != null) {
                    List<AccessibilityNodeInfo> nodes = rootNode.findAccessibilityNodeInfosByViewId("com.whatsapp:id/send");
                    if (!((List<?>) nodes).isEmpty()) {
                        AccessibilityNodeInfo node = nodes.get(0);
                        // Perform a click action on the send button
                        node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    }
                }
            }
        }
    }

    @Override
    public void onInterrupt() {
        // Do nothing
    }
}

