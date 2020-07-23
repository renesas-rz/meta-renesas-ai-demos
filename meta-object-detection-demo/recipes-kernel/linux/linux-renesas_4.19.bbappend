FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}/:"

SRC_URI += " \
	file://usb_video_class.cfg \
	file://0001-dt-bindings-display-renesas-Add-RZ-G2N-HDMI-TX-DT-bi.patch \
	file://0002-drm-rcar-du-Fix-PHY-configure-registers.patch \
	file://0003-arm64-dts-renesas-r8a774e1-hihope-rzg2h-enable-HDMI-.patch \
	file://0004-dt-bindings-display-renesas-Add-RZ-G2H-HDMI-TX-DT-bi.patch \
	file://0005-arm64-dts-renesas-r8a774e1-Add-HDMI-encoder-instance.patch \
"
