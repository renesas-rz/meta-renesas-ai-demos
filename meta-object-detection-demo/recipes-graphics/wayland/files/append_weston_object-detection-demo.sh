#!/bin/sh

WESTON_FILE=${1}
OBJECT_DETECTION_INSTALL_DIR=${2}

cat >> "${WESTON_FILE}" <<-EOF

[launcher]
icon=${OBJECT_DETECTION_INSTALL_DIR}/icons/cpu-object.png
path=${OBJECT_DETECTION_INSTALL_DIR}/scripts/cpu-object.sh

[launcher]
icon=${OBJECT_DETECTION_INSTALL_DIR}/icons/tpu-object.png
path=${OBJECT_DETECTION_INSTALL_DIR}/scripts/tpu-object.sh

[launcher]
icon=${OBJECT_DETECTION_INSTALL_DIR}/icons/cpu-face.png
path=${OBJECT_DETECTION_INSTALL_DIR}/scripts/cpu-face.sh

[launcher]
icon=${OBJECT_DETECTION_INSTALL_DIR}/icons/tpu-face.png
path=${OBJECT_DETECTION_INSTALL_DIR}/scripts/tpu-face.sh
EOF
