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

/*Nifi ProcessGroup component collapse*/
const nifiProcessGroup = (props) => {
    return (
        <Collapse
            bordered={false}
            key="processGroup"
            expandIcon={({isActive}) => <Icon type="caret-right" rotate={isActive ? 90 : 0}/>}>

            <Panel
                header={"Process Groups".toUpperCase() + " (" + props.count + ")"}
                key={''} style={customPanelStyle}>

                <Table
                    size='small' columns={tableColumns.processGroupColumns}
                    dataSource={props.data} bordered={true}/>
            </Panel>
        </Collapse>
    );
};

export default nifiProcessGroup;
