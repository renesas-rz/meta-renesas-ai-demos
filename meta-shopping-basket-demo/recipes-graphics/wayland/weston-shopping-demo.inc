FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SHOPPING_BASKET_DEMO_INSTALL_DIRECTORY ?= "/opt/shopping-basket-demo"

SRC_URI_append += "file://append_weston_shopping-basket-demo.sh"

do_install_append() {
	if echo "${BBLAYERS}" | grep -wq "meta-ai-demos-common" && \
	echo "${IMAGE_INSTALL}" | grep -wq "shopping-basket-demo" ; then
		${WORKDIR}/append_weston_shopping-basket-demo.sh ${D}/${sysconfdir}/xdg/weston/weston.ini ${SHOPPING_BASKET_DEMO_INSTALL_DIRECTORY}
	fi
}
