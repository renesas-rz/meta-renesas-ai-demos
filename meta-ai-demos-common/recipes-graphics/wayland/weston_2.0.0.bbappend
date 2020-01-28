FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

DEMOS_DESKTOP_COMMON_INSTALL_DIRECTORY ?= "/opt/demos-desktop-common"

SRC_URI_append += " \
	file://populate_weston-base.sh \
	file://populate_weston-720p.sh \
	file://images/ \
	file://scripts/ \
"

do_install_append_ek874() {
	${WORKDIR}/populate_weston-720p.sh ${D}/${sysconfdir}/xdg/weston/weston.ini ${DEMOS_DESKTOP_COMMON_INSTALL_DIRECTORY}
}

do_install_append() {
	${WORKDIR}/populate_weston-base.sh ${D}/${sysconfdir}/xdg/weston/weston.ini ${DEMOS_DESKTOP_COMMON_INSTALL_DIRECTORY}
	install -d ${D}${DEMOS_DESKTOP_COMMON_INSTALL_DIRECTORY}
	install -m 444 ${WORKDIR}/images/* ${D}${DEMOS_DESKTOP_COMMON_INSTALL_DIRECTORY}
	install -m 555 ${WORKDIR}/scripts/* ${D}${DEMOS_DESKTOP_COMMON_INSTALL_DIRECTORY}
}

FILES_${PN} += " \
	${DEMOS_DESKTOP_COMMON_INSTALL_DIRECTORY} \
"
