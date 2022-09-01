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

/*Nifi Connection component collapse*/
const nifiConnection = (props) => {

    return (
        <Collapse
            bordered={false}
            key="connection"
            expandIcon={({isActive}) => <Icon type="caret-right" rotate={isActive ? 90 : 0}/>}>

            <Panel
                header={"Connections".toUpperCase() + " (" + props.count + ")"}
                key={''} style={customPanelStyle}>

                <Table
                    size='small' columns={tableColumns.connectionColumns}
                    dataSource={props.data} bordered={true}/>

            </Panel>
        </Collapse>
    );

};

export default nifiConnection;
