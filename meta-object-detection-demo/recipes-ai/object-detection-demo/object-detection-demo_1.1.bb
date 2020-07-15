SUMMARY = "Object Detection Demo"
DESCRIPTION = "Build and install the RZ/G2 Object Detection Demo application"

inherit populate_sdk_qt5
require recipes-qt/qt5/qt5.inc

LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/${LICENSE};md5=801f80980d171dd6425610833a22dbe6"

DEPENDS = "qtmultimedia opencv gstreamer tensorflow-lite google-coral"
RDEPENDS_${PN} = "libopencv-core libopencv-videoio libopencv-imgcodecs libopencv-imgproc google-coral"

OBJECT_DETECTION_DEMO_INSTALL_DIRECTORY ?= "/opt/object-detection-demo"

OBJECT_DETECTION_DEMO_REPO ?= "github.com/renesas-rz/rzg-object-detection-demo.git"
OBJECT_DETECTION_DEMO_REPO_PROTOCOL ?= "https"
OBJECT_DETECTION_DEMO_REPO_BRANCH ?= "master"

SRC_URI = " \
	git://${OBJECT_DETECTION_DEMO_REPO};protocol=${OBJECT_DETECTION_DEMO_REPO_PROTOCOL};branch=${OBJECT_DETECTION_DEMO_REPO_BRANCH};destsuffix=git/object_detection_demo;name=object-detection-demo\
	git://github.com/google-coral/edgetpu.git;protocol=git;destsuffix=git/edgetpu;name=edgetpu \
	file://coco_labels.txt \
	file://face_label.txt \
	file://icons/ \
	file://populate_scripts.sh \
"

SRCREV_FORMAT = "object-detection-demo_edgetpu"

SRCREV_object-detection-demo ?= "2e1e7d495fa8a8cc069db8cef7c725376b2c3eaf"
SRCREV_edgetpu = "75e675633c2110a991426c8afa64f122b16ac372"

S = "${WORKDIR}/git/object_detection_demo"

do_configure_prepend () {
	export SDKTARGETSYSROOT="../recipe-sysroot"
}

do_install_append () {
	install -d ${D}${OBJECT_DETECTION_DEMO_INSTALL_DIRECTORY}
	install -m 444 ${WORKDIR}/git/edgetpu/test_data/mobilenet_ssd_v2_coco_quant_postprocess.tflite ${D}${OBJECT_DETECTION_DEMO_INSTALL_DIRECTORY}
	install -m 444 ${WORKDIR}/git/edgetpu/test_data/mobilenet_ssd_v2_coco_quant_postprocess_edgetpu.tflite ${D}${OBJECT_DETECTION_DEMO_INSTALL_DIRECTORY}
	install -m 444 ${WORKDIR}/git/edgetpu/test_data/mobilenet_ssd_v2_face_quant_postprocess.tflite ${D}${OBJECT_DETECTION_DEMO_INSTALL_DIRECTORY}
	install -m 444 ${WORKDIR}/git/edgetpu/test_data/mobilenet_ssd_v2_face_quant_postprocess_edgetpu.tflite ${D}${OBJECT_DETECTION_DEMO_INSTALL_DIRECTORY}
	install -m 444 ${WORKDIR}/git/edgetpu/test_data/bird.bmp ${D}${OBJECT_DETECTION_DEMO_INSTALL_DIRECTORY}
	install -m 444 ${WORKDIR}/git/edgetpu/test_data/cat.bmp ${D}${OBJECT_DETECTION_DEMO_INSTALL_DIRECTORY}
	install -m 444 ${WORKDIR}/git/edgetpu/test_data/face.jpg ${D}${OBJECT_DETECTION_DEMO_INSTALL_DIRECTORY}
	install -m 444 ${WORKDIR}/git/edgetpu/test_data/grace_hopper.bmp ${D}${OBJECT_DETECTION_DEMO_INSTALL_DIRECTORY}
	install -m 444 ${WORKDIR}/git/edgetpu/test_data/hot_dog.jpg ${D}${OBJECT_DETECTION_DEMO_INSTALL_DIRECTORY}
	install -m 444 ${WORKDIR}/git/edgetpu/test_data/owl.jpg ${D}${OBJECT_DETECTION_DEMO_INSTALL_DIRECTORY}
	install -m 444 ${WORKDIR}/git/edgetpu/test_data/parrot.jpg ${D}${OBJECT_DETECTION_DEMO_INSTALL_DIRECTORY}
	install -m 444 ${WORKDIR}/git/edgetpu/test_data/pets.jpg ${D}${OBJECT_DETECTION_DEMO_INSTALL_DIRECTORY}
	install -m 444 ${WORKDIR}/coco_labels.txt ${D}${OBJECT_DETECTION_DEMO_INSTALL_DIRECTORY}
	install -m 444 ${WORKDIR}/face_label.txt ${D}${OBJECT_DETECTION_DEMO_INSTALL_DIRECTORY}
	install -m 555 ${B}/object_detection_demo ${D}${OBJECT_DETECTION_DEMO_INSTALL_DIRECTORY}

	if echo "${BBLAYERS}" | grep -wq "meta-ai-demos-common" ; then
		install -d ${D}${OBJECT_DETECTION_DEMO_INSTALL_DIRECTORY}/icons
		install -d ${D}${OBJECT_DETECTION_DEMO_INSTALL_DIRECTORY}/scripts
		install -m 444 ${WORKDIR}/icons/* ${D}${OBJECT_DETECTION_DEMO_INSTALL_DIRECTORY}/icons
		${WORKDIR}/populate_scripts.sh ${OBJECT_DETECTION_DEMO_INSTALL_DIRECTORY}
		install -m 555 ${B}/scripts/* ${D}${OBJECT_DETECTION_DEMO_INSTALL_DIRECTORY}/scripts
	fi
}

FILES_${PN} = " \
	${OBJECT_DETECTION_DEMO_INSTALL_DIRECTORY} \
"
