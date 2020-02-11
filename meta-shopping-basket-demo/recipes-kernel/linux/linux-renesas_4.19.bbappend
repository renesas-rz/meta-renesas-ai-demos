FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}/:"

SRC_URI += " \
	file://usb_video_class.cfg \
	file://0006-drm-rcar-du-Add-HDMI-resolution-check-for-RZ-G2.patch \
"

SRC_URI_append_hihope-rzg2m = " \
	file://0003-dt-bindings-display-renesas-Add-RZ-G2M-HDMI-TX-DT-bi.patch \
	file://0004-arm64-dts-renesas-r8a774a1-Add-HDMI-encoder-instance.patch \
	file://0005-arm64-dts-renesas-hihope-common-Enable-HDMI-output-s.patch \
"
