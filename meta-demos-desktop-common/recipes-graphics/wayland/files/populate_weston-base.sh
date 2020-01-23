#!/bin/sh

WESTON_FILE=${1}
DESKTOP_INSTALL_DIR=${2}

cat >> "${WESTON_FILE}" <<-EOF

[shell]
background-type=scale
background-color=0xff002200
background-image=${DESKTOP_INSTALL_DIR}/Renesas-Screen.jpg

[launcher]
icon=${DESKTOP_INSTALL_DIR}/reboot.png
path=${DESKTOP_INSTALL_DIR}/reboot.sh
EOF
