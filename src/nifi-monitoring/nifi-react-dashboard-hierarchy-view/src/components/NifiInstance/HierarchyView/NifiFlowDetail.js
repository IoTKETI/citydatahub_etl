import React from 'react';
import {Badge, Button, Card, Col, Collapse, Empty, Icon, Row, Spin, Statistic, Table, Tabs} from 'antd';
import Aux from '../../../hoc/Aux/Aux';
import NifiProcessGroup from '../../NifiProcessGroup/NifiProcessGroup';
import NifiRemoteProcessGroup from '../../NifiRemoteProcessGroup/NifiRemoteProcessGroup';
import NifiProcessor from '../../NifiProcessor/NifiProcessor';
import NifiConnection from '../../NifiConnection/NifiConnection';
import NifiOutputPort from '../../NifiOutputPort/NifiOutputPort';
import NifiInputPort from '../../NifiInputPort/NifiInputPort';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import * as tableColumns from "../../../TableColumns";

import {
    faAlignJustify,
    faArrowAltCircleDown,
    faArrowAltCircleUp,
    faArrowDown,
    faArrowUp,
    faChessBoard,
    faExclamationTriangle,
    faExternalLinkAlt,
    faList,
    faPlay,
    faStop
} from "@fortawesome/free-solid-svg-icons";
import {Link} from "react-router-dom";
import * as endpoint from "../../../Endpoints";

const {Panel} = Collapse;
const customFlowPanelStyle = {
    background: '#f7f7f7',
    borderRadius: 4,
    border: 0,
    marginBottom: '15px',
    overflow: 'hidden',
};
const {TabPane} = Tabs;

const NifiFlowDetail = (props) => {

    function callback(key) {
        console.log(key);
    }

    // let show = <Spin tip="Loading..."> </Spin>;
    let show = 'Click on process group to show the detail here.';
    // let show = null;

    let contentList = {
        status: null,
        components: null,
        bulletins: null,
    };

    contentList.status = (
        <Row gutter={16}>
            <Col span={6}>
                <Card>
                    <Statistic
                        title={"Active Threads".toUpperCase()}
                        value={props.flow.flow.flowInfo.status.aggregateSnapshot.activeThreadCount}
                        valueStyle={{color: 'rgb(125, 199, 160)'}}
                        prefix={<FontAwesomeIcon icon={faChessBoard} size="xs"/>}
                        // suffix="%"
                    />
                </Card>
            </Col>
            <Col span={6}>
                <Card>
                    <Statistic
                        title={"Queued".toUpperCase()}
                        value={props.flow.flow.flowInfo.status.aggregateSnapshot.queued}
                        valueStyle={{color: '#728e9b'}}
                        prefix={<FontAwesomeIcon icon={faList} size="xs"/>}
                        // suffix="%"
                    />
                </Card>
            </Col>
            <Col span={6}>
                <Card>
                    <Statistic
                        title={"Input".toUpperCase()}
                        value={props.flow.flow.flowInfo.status.aggregateSnapshot.input}
                        valueStyle={{color: 'rgb(125, 199, 160)'}}
                        prefix={<FontAwesomeIcon icon={faArrowAltCircleDown} size="xs"/>}
                    />
                </Card>
            </Col>
            <Col span={6}>
                <Card>
                    <Statistic
                        title={"Output".toUpperCase()}
                        value={props.flow.flow.flowInfo.status.aggregateSnapshot.output}
                        valueStyle={{color: '#cf9f5d'}}
                        prefix={<FontAwesomeIcon icon={faArrowAltCircleUp} size="xs"/>}
                    />
                </Card>
            </Col>
            <Col span={6}>
                <Card>
                    <Statistic
                        title={"Read".toUpperCase()}
                        value={props.flow.flow.flowInfo.status.aggregateSnapshot.read}
                        valueStyle={{color: '#cf9f5d'}}
                        prefix={<FontAwesomeIcon icon={faAlignJustify} size="xs"/>}
                    />
                </Card>
            </Col>
            <Col span={6}>
                <Card>
                    <Statistic
                        title={"Written".toUpperCase()}
                        value={props.flow.flow.flowInfo.status.aggregateSnapshot.written}
                        valueStyle={{color: '#728e9b'}}
                        prefix={<FontAwesomeIcon icon={faAlignJustify} size="xs"/>}
                    />
                </Card>
            </Col>
            <Col span={6}>
                <Card>
                    <Statistic
                        title={"Received".toUpperCase()}
                        value={props.flow.flow.flowInfo.status.aggregateSnapshot.received}
                        valueStyle={{color: '#728e9b'}}
                        prefix={<FontAwesomeIcon icon={faArrowDown} size="xs"/>}
                    />
                </Card>
            </Col>
            <Col span={6}>
                <Card>
                    <Statistic
                        title={"Sent".toUpperCase()}
                        value={props.flow.flow.flowInfo.status.aggregateSnapshot.sent}
                        valueStyle={{color: '#728e9b'}}
                        prefix={<FontAwesomeIcon icon={faArrowUp} size="xs"/>}
                    />
                </Card>
            </Col>

        </Row>
    );
    contentList.components = (
        <Row gutter={16}>
            <Col span={6}>
                <Card>
                    <Statistic
                        title="RUNNING COMPONENTS"
                        value={props.flow.flow.flowInfo.component.runningCount}
                        valueStyle={{color: 'rgb(125, 199, 160)'}}
                        prefix={<FontAwesomeIcon icon={faPlay} size="xs"/>}
                        // suffix="%"
                    />
                </Card>
            </Col>
            <Col span={6}>
                <Card>
                    <Statistic
                        title="STOPPED COMPONENTS"
                        value={props.flow.flow.flowInfo.component.stoppedCount}
                        valueStyle={{color: 'rgb(209, 134, 134)'}}
                        prefix={<FontAwesomeIcon icon={faStop} size="xs"/>}
                    />
                </Card>
            </Col>
            <Col span={6}>
                <Card>
                    <Statistic
                        title="INVALID COMPONENTS"
                        value={props.flow.flow.flowInfo.component.invalidCount}
                        valueStyle={{color: '#cf9f5d'}}
                        prefix={<FontAwesomeIcon icon={faExclamationTriangle} size="xs"/>}
                    />
                </Card>
            </Col>
            <Col span={6}>
                <Card>
                    <Statistic
                        title="DISABLED COMPONENTS"
                        value={props.flow.flow.flowInfo.component.disabledCount}
                        valueStyle={{color: '#cf9f5d'}}
                        prefix={<FontAwesomeIcon icon={faExclamationTriangle} size="xs"/>}
                    />
                </Card>
            </Col>
            <Col span={6}>
                <Card>
                    <Statistic
                        title={"Input port count".toUpperCase()}
                        value={props.flow.flow.flowInfo.component.inputPortCount}
                        valueStyle={{color: '#cf9f5d'}}
                        prefix={<FontAwesomeIcon icon={faArrowAltCircleDown} size="xs"/>}
                    />
                </Card>
            </Col>
            <Col span={6}>
                <Card>
                    <Statistic
                        title={"Output Port Count".toUpperCase()}
                        value={props.flow.flow.flowInfo.component.outputPortCount}
                        valueStyle={{color: '#728e9b'}}
                        prefix={<FontAwesomeIcon icon={faArrowAltCircleUp} size="xs"/>}
                    />
                </Card>
            </Col>
            <Col span={6}>
                <Card>
                    <Statistic
                        title={"Active Remote Port Count".toUpperCase()}
                        value={props.flow.flow.flowInfo.component.activeRemotePortCount}
                        valueStyle={{color: 'rgb(125, 199, 160)'}}
                        prefix={<FontAwesomeIcon icon={faList} size="xs"/>}
                        // suffix="%"
                    />
                </Card>
            </Col>
            <Col span={6}>
                <Card>
                    <Statistic
                        title={"Inactive Remote Port Count".toUpperCase()}
                        value={props.flow.flow.flowInfo.component.inactiveRemotePortCount}
                        valueStyle={{color: 'rgb(209, 134, 134)'}}
                        prefix={<FontAwesomeIcon icon={faList} size="xs"/>}
                        // suffix="%"
                    />
                </Card>
            </Col>
        </Row>
    );

    let flowBulletins = [];
    if (props.flow.flow.flowInfo.bulletins.length) {
        try {
            flowBulletins = props.flow.flow.flowInfo.bulletins.map(bulletin => {
                return {
                    key: bulletin.bulletin.id,
                    timestamp: bulletin.bulletin.timestamp,
                    level: bulletin.bulletin.level,
                    category: bulletin.bulletin.category,
                    message: bulletin.bulletin.message,
                };
            });
            console.log(flowBulletins);
            contentList.bulletins = (
                <Table
                    size='small' columns={tableColumns.bulletinsColumns}
                    dataSource={flowBulletins} bordered={true}/>
            );
        } catch (e) {
            console.log('Meme error');
            contentList.bulletins = (
                <Card>
                    <Empty
                        description={'Components with bulletins(errors) may be deleted on NiFi! And the error indicator will be disappear within 5 minutes.'}
                        image={Empty.PRESENTED_IMAGE_SIMPLE}/>
                </Card>

            );
        }

    } else {
        contentList.bulletins = (
            <Card>
                <Empty description={'5분 내 나타난 에러 로그 기록이 없습니다!'} image={Empty.PRESENTED_IMAGE_SIMPLE}
                       style={{color: 'rgba(125, 199, 160, 1)'}}/>
            </Card>
        );
    }

    let processorGroupDataToShow = [];
    let remoteProcessorGroupDataToShow = [];
    let processorDataToShow = [];
    let inputPortDataToShow = [];
    let outputPortDataToShow = [];
    let connectionDataToShow = [];

    if (props.flow.flow.processGroups.length) {
        const data = props.flow.flow.processGroups.map(component => {
            return {
                key: component.id,
                name: component.component.name,
                transferred: component.status.aggregateSnapshot.transferred,
                readWrite: component.status.aggregateSnapshot.read + ' / ' + component.status.aggregateSnapshot.written,
                input: component.status.aggregateSnapshot.input,
                output: component.status.aggregateSnapshot.output,
                sent: component.status.aggregateSnapshot.sent,
                received: component.status.aggregateSnapshot.received,
            };
        });

        processorGroupDataToShow = (
            <NifiProcessGroup data={data} count={props.flow.flow.processGroups.length}/>
        );
    }

    if (props.flow.flow.remoteProcessGroups.length) {
        const data = props.flow.flow.remoteProcessGroups.map(component => {
            return {
                key: component.id,
                name: component.component.name,
                targetUri: component.status.targetUri,
                transmitting: component.status.transmissionStatus,
                sent: component.status.aggregateSnapshot.sent,
                received: component.status.aggregateSnapshot.received,
            };
        });

        remoteProcessorGroupDataToShow = (
            <NifiRemoteProcessGroup data={data} count={props.flow.flow.remoteProcessGroups.length}/>
        );
    }

    if (props.flow.flow.processors.length) {
        const data = props.flow.flow.processors.map(component => {
            return {
                key: component.id,
                name: component.component.name,
                type: component.status.aggregateSnapshot.type,
                processGroup: props.flow.flow.flowInfo.status.name,
                runStatus: component.status.aggregateSnapshot.runStatus,
                input: component.status.aggregateSnapshot.input,
                output: component.status.aggregateSnapshot.output,
                readWrite: component.status.aggregateSnapshot.read + ' / ' + component.status.aggregateSnapshot.written,
                tasksDuration: component.status.aggregateSnapshot.tasks + ' / ' + component.status.aggregateSnapshot.tasksDuration,
            };
        });

        processorDataToShow = (
            <NifiProcessor data={data} count={props.flow.flow.processors.length}/>
        );
    }

    if (props.flow.flow.inputPorts.length) {
        const data = props.flow.flow.inputPorts.map(component => {
            return {
                key: component.id,
                name: component.status.aggregateSnapshot.name,
                runStatus: component.status.aggregateSnapshot.runStatus,
                input: component.status.aggregateSnapshot.input,
            };
        });

        inputPortDataToShow = (
            <NifiInputPort count={props.flow.flow.inputPorts.length} data={data}/>
        );
    }

    if (props.flow.flow.outputPorts.length) {
        const data = props.flow.flow.outputPorts.map(component => {
            return {
                key: component.id,
                name: component.status.aggregateSnapshot.name,
                runStatus: component.status.aggregateSnapshot.runStatus,
                output: component.status.aggregateSnapshot.output,
            };
        });

        outputPortDataToShow = (
            <NifiOutputPort data={data} count={props.flow.flow.outputPorts.length}/>
        );
    }

    if (props.flow.flow.connections.length) {
        const data = props.flow.flow.connections.map(component => {
            return {
                key: component.id,
                name: component.component.name,
                sourceName: component.component.source.name,
                destinationName: component.component.destination.name,
                input: component.status.aggregateSnapshot.input,
                output: component.status.aggregateSnapshot.output,
                queued: component.status.aggregateSnapshot.queued,
            };
        });
        connectionDataToShow = (
            <NifiConnection data={data} count={props.flow.flow.connections.length}/>
        );
    }

    const header = (
        <div>
            <span style={{fontWeight: 'bold', fontSize: '20px', color: 'rgb(24, 144, 255)'}}>
                {props.flow.flow.flowInfo.status.name}
            </span>
            &nbsp;&nbsp;&nbsp;
            <Link
                target="_blank"
                to={{pathname: endpoint.LINK_TO_NIFI_INSTANCE + props.flow.id + "&componentIds="}}>
                <Button key="3"
                        type="danger"
                        size="small"><FontAwesomeIcon icon={faExternalLinkAlt} size="xs"/></Button>
            </Link>
        </div>);


    const allFlowToShow = (
        <Panel
            // header={props.flow.flow.flowInfo.status.name}
            header={header}
            key={1} style={customFlowPanelStyle}>

            <div>
                <Tabs defaultActiveKey="1" onChange={callback}>
                    <TabPane tab="Status" key="1">
                        {contentList.status}
                    </TabPane>
                    <TabPane tab="Components" key="2">
                        {contentList.components}
                    </TabPane>
                    <TabPane tab={<Badge
                        count={props.flow.flow.flowInfo.bulletins.length}><span>Bulletins&nbsp;&nbsp;&nbsp;</span></Badge>}
                             key="3">
                        {contentList.bulletins}
                    </TabPane>
                </Tabs>
            </div>

            {processorGroupDataToShow}

            {remoteProcessorGroupDataToShow}

            {processorDataToShow}

            {inputPortDataToShow}

            {outputPortDataToShow}

            {connectionDataToShow}

        </Panel>
    );

    if (props.spin)
        return (
            <div style={{
                zIndex: 99999, paddingTop: "50px"
            }}><Spin style={{paddingTop: '0px', zIndex: 2}} tip={"Loading..."}> </Spin></div>
        );

    if (allFlowToShow) {
        show = (
            <Aux>
                <Collapse
                    bordered={false}
                    defaultActiveKey={['1']}
                    expandIcon={({isActive}) => <Icon type="caret-right" rotate={isActive ? 90 : 0}/>}>

                    <Aux>
                        <Collapse
                            bordered={false}
                            defaultActiveKey={['1']}
                            expandIcon={({isActive}) => <Icon type="caret-right" rotate={isActive ? 90 : 0}/>}>

                            {allFlowToShow}

                        </Collapse>
                    </Aux>
                </Collapse>
            </Aux>
        );
    }


    return show;

};

export default NifiFlowDetail;
