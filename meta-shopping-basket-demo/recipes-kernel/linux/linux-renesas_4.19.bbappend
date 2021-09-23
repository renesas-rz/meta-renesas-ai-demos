FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}/:"

SRC_URI_append_hihope-rzg2m += "file://usb_video_class.cfg"

SRC_URI_append_smarc-rzg2l += " \
	file://0001-arm64-dts-renesas-r9a07g044l2-smarc-Add-CSI-camera-s.patch \
"
