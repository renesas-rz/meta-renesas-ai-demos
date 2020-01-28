FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

OBJECT_DETECTION_DEMO_INSTALL_DIRECTORY ?= "/opt/object-detection-demo"

SRC_URI_append += " \
        file://append_weston_object-detection-demo.sh \
"
do_install_append() {
	if echo "${BBLAYERS}" | grep -wq "meta-ai-demos-common" && \
	echo "${IMAGE_INSTALL}" | grep -wq "object-detection-demo" ; then
		${WORKDIR}/append_weston_object-detection-demo.sh ${D}/${sysconfdir}/xdg/weston/weston.ini ${OBJECT_DETECTION_DEMO_INSTALL_DIRECTORY}
	fi
}
