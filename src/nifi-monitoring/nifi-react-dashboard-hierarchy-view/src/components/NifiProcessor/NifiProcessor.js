import React from 'react';
import {Collapse, Icon, Table} from "antd";
import * as tableColumns from "../../TableColumns";

const {Panel} = Collapse;
const customPanelStyle = {
    background: '#f7f7f7',
    borderRadius: 4,
    border: 0,
    overflow: 'hidden',
};


/*Nifi Processor component collapse*/
const nifiProcessor = (props) => {
    return (
        <Collapse
            bordered={false}
            key="processor"
            expandIcon={({isActive}) => <Icon type="caret-right" rotate={isActive ? 90 : 0}/>}>

            <Panel
                header={"Processors".toUpperCase() + " (" + props.count + ")"}
                key={''} style={customPanelStyle}>

                <Table
                    size='small' columns={tableColumns.processorColumns}
                    dataSource={props.data} bordered={true}/>
            </Panel>
        </Collapse>
    );

};

export default nifiProcessor;
