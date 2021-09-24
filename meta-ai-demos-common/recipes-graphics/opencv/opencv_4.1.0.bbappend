PACKAGECONFIG_smarc-rzg2l = " \
	gapi python3 eigen jpeg png tiff v4l libv4l samples tbb gphoto2 \
	${@bb.utils.contains("DISTRO_FEATURES", "x11", "gtk", "", d)} \
	${@bb.utils.contains("LICENSE_FLAGS_WHITELIST", "commercial", "libav", "", d)}" \
"
