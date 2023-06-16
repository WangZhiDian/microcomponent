#!/bin/bash

SERVICE_INSTALL_DIR=/opt/cloud/price_control
SERVICE_INSTALL_BIN_DIR={{SERVICE_INSTALL_DIR}}/bin

if [ -f ${SERVICE_INSTALL_BIN_DIR}/check_status.sh ]; then
    bash ${SERVICE_INSTALL_BIN_DIR}/check_status.sh
    [[ $? -ne 0 ]] && bash ${SERVICE_INSTALL_BIN_DIR}/start.sh && exit 1
fi