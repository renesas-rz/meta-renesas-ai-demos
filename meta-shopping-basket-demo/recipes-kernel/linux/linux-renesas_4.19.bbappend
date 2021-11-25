FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}/:"

SRC_URI_append_hihope-rzg2m = " \
	file://usb_video_class.cfg \
	file://0002-Revert-media-ov5645-fix-streaming-on-off-sequence-ba.patch \
	file://0003-Revert-media-ov5645-change-the-way-of-setting-virtua.patch \
	file://0004-Revert-media-i2c-ov5645-add-virtual_channel-paramete.patch \
"

SRC_URI_append_ek874 = " \
	file://usb_video_class.cfg \
	file://0002-Revert-media-ov5645-fix-streaming-on-off-sequence-ba.patch \
	file://0003-Revert-media-ov5645-change-the-way-of-setting-virtua.patch \
	file://0004-Revert-media-i2c-ov5645-add-virtual_channel-paramete.patch \
"

SRC_URI_append_smarc-rzg2l = " \
	file://0001-arm64-dts-renesas-r9a07g044l2-smarc-Add-CSI-camera-s.patch \
"

SRC_URI_append_smarc-rzg2lc = " \
	file://0001-arm64-dts-renesas-r9a07g044l2-smarc-Add-CSI-camera-s.patch \
"

SRC_URI_append = " \
	file://0007-ov5645-Add-VGA-720x480-and-720p-resloutions.patch \
	file://0008-ov5645-Add-pixel-rate-support-for-each-mode.patch \
	file://0009-media-i2c-ov5645-Add-support-for-SVGA.patch \
	file://0010-media-i2c-ov5645-Add-AF-support.patch \
"
