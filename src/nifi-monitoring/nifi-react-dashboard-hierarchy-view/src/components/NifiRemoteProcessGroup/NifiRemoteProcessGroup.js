import React from 'react';
import * as tableColumns from "../../TableColumns";
import {Collapse, Icon, Table} from "antd";

const {Panel} = Collapse;
const customPanelStyle = {
    background: '#f7f7f7',
    borderRadius: 4,
    border: 0,
    overflow: 'hidden',
};

/*Nifi RemoteProcessGroup component collapse*/
const nifiRemoteProcessGroup = (props) => {

    return (
        <Collapse
            bordered={false}
            key="remoteProcessGroup"
            expandIcon={({isActive}) => <Icon type="caret-right" rotate={isActive ? 90 : 0}/>}>

            <Panel
                header={"Remote Process Groups".toUpperCase() + " (" + props.count + ")"}
                key={''} style={customPanelStyle}>

                <Table
                    size='small' columns={tableColumns.remoteProcessGroupColumns}
                    dataSource={props.data} bordered={true}/>

            </Panel>
        </Collapse>
    );
};

export default nifiRemoteProcessGroup;
