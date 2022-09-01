import React, {Component} from 'react';
import Aux from '../../hoc/Aux/Aux'
import {Card, Col, Progress, Row, Spin, Statistic} from "antd";
import axios from "../../axios-spring-boot-api-instance";
import * as endpoint from '../../Endpoints';

import {FontAwesomeIcon} from '@fortawesome/react-fontawesome'
import {faChessBoard, faExclamationTriangle, faList, faMemory, faPlay, faStop} from '@fortawesome/free-solid-svg-icons'

class Dashboard extends Component {

    state = {
        flow_status: null,
        hardware_status: null,

    };

    componentDidMount() {
        /*Start Get status of Nifi Instance when first load this page*/
        axios.get(endpoint.FLOW_STATUS)
            .then(res => {
                console.log(res);
                this.setState({flow_status: res.data});
            });

        /*End get status of Nifi Instance when first load this page*/

        /*Get Hardware status of Nifi Instance when first load this page*/
        axios.get(endpoint.GET_HARDWARE_STATUS)
            .then(res => {
                console.log(res);
                this.setState({hardware_status: res.data});
            });
        /*End Get Hardware status of Nifi Instance when first load this page*/
    }

    render() {
        let show = <Spin style={{paddingTop: '50%'}} tip="Loading..."> </Spin>;

        if (this.state.flow_status && this.state.hardware_status) {
            show = (
                <Aux>
                    <div style={{ minHeight: 360}}>
                        <div style={{background: '#ECECEC', padding: '30px'}}>
                            <h3>All Flow Information</h3>
                            <Row gutter={16}>
                                <Col span={6}>
                                    <Card>
                                        <Statistic
                                            title="RUNNING COMPONENTS"
                                            value={this.state.flow_status.data.runningCount}
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
                                            value={this.state.flow_status.data.stoppedCount}
                                            valueStyle={{color: 'rgb(209, 134, 134)'}}
                                            prefix={<FontAwesomeIcon icon={faStop} size="xs"/>}
                                        />
                                    </Card>
                                </Col>
                                <Col span={6}>
                                    <Card>
                                        <Statistic
                                            title="INVALID COMPONENTS"
                                            value={this.state.flow_status.data.invalidCount}
                                            valueStyle={{color: '#cf9f5d'}}
                                            prefix={<FontAwesomeIcon icon={faExclamationTriangle} size="xs"/>}
                                        />
                                    </Card>
                                </Col>
                                <Col span={6}>
                                    <Card>
                                        <Statistic
                                            title="DISABLED COMPONENTS"
                                            value={this.state.flow_status.data.disabledCount}
                                            valueStyle={{color: '#cf9f5d'}}
                                            prefix={<FontAwesomeIcon icon={faExclamationTriangle} size="xs"/>}
                                        />
                                    </Card>
                                </Col>
                                <Col span={6}>
                                    <Card>
                                        <Statistic
                                            title={"Active Thread".toUpperCase()}
                                            value={this.state.flow_status.data.activeThreadCount}
                                            valueStyle={{color: '#cf9f5d'}}
                                            prefix={<FontAwesomeIcon icon={faChessBoard} size="xs"/>}
                                        />
                                    </Card>
                                </Col>
                                <Col span={6}>
                                    <Card>
                                        <Statistic
                                            title={"Total Queued Data".toUpperCase()}
                                            value={this.state.flow_status.data.flowFilesQueued}
                                            valueStyle={{color: '#728e9b'}}
                                            prefix={<FontAwesomeIcon icon={faList} size="xs"/>}
                                        />
                                    </Card>
                                </Col>

                                <Col span={6}>
                                    <Card>
                                        <Statistic
                                            title={"Total Bytes Queued".toUpperCase()}
                                            value={this.state.flow_status.data.bytesQueued + " bytes"}
                                            valueStyle={{color: '#728e9b'}}
                                            prefix={<FontAwesomeIcon icon={faList} size="xs"/>}
                                        />
                                    </Card>
                                </Col>

                                <Col span={6}>
                                    <Card>
                                        <Statistic
                                            title={"Total Queued Data".toUpperCase()}
                                            value={this.state.flow_status.data.queued}
                                            valueStyle={{color: '#728e9b'}}
                                            prefix={<FontAwesomeIcon icon={faList} size="xs"/>}
                                        />
                                    </Card>
                                </Col>
                            </Row>
                        </div>

                        <div style={{background: '#ECECEC', padding: '0px 30px 30px 30px'}}>
                            <h3>System Monitoring</h3>
                            <Row gutter={16}>
                                <Col span={6}>
                                    <Card>
                                        <Statistic
                                            title={"Memory Available".toUpperCase()}
                                            value={this.state.hardware_status.memory_available}
                                            valueStyle={{color: 'rgb(125, 199, 160)'}}
                                            prefix={<FontAwesomeIcon icon={faMemory} size="xs"/>}
                                        />
                                    </Card>
                                </Col>
                                <Col span={6}>
                                    <Card>
                                        <Statistic
                                            title={"Total Memory Usage".toUpperCase()}
                                            value={this.state.hardware_status.total_memory_usage}
                                            valueStyle={{color: 'rgb(209, 134, 134)'}}
                                            prefix={<FontAwesomeIcon icon={faMemory} size="xs"/>}
                                        />
                                    </Card>
                                </Col>
                                <Col span={6}>
                                    <Card>
                                        <Statistic
                                            title={"Memory Used By Nifi".toUpperCase()}
                                            value={this.state.hardware_status.nifi_memory_usage}
                                            valueStyle={{color: '#cf9f5d'}}
                                            prefix={<FontAwesomeIcon icon={faMemory} size="xs"/>}
                                        />
                                    </Card>
                                </Col>

                            </Row>

                            <Row gutter={16}>
                                <Col span={6}>
                                    <Card>
                                        <h4>Memory Used By Nifi (%)</h4>
                                        <div style={{textAlign: "center"}}>
                                            <Progress showInfo={true} strokeColor="#cf9f5d" status="active"
                                                      type="circle" strokeLinecap="square"
                                                      percent={this.state.hardware_status.nifi_memory_usage_percent}/>
                                        </div>
                                    </Card>
                                </Col>
                                <Col span={6}>
                                    <Card>
                                        <h4>CPU used by Nifi (%)</h4>
                                        <div style={{textAlign: "center"}}>
                                            <Progress showInfo={true} strokeColor="rgb(125, 199, 160)" status="active"
                                                      type="circle" strokeLinecap="square"
                                                      percent={this.state.hardware_status.nifi_cpu_usage}/>
                                        </div>
                                    </Card>
                                </Col>
                                <Col span={6}>
                                    <Card>
                                        <h4>Total CPU usage (%)</h4>
                                        <div style={{textAlign: "center"}}>
                                            <Progress showInfo={true} status="active" type="circle"
                                                      strokeLinecap="square"
                                                      percent={this.state.hardware_status.total_cpu_usage}/>
                                        </div>
                                    </Card>
                                </Col>

                            </Row>
                        </div>
                    </div>

                </Aux>
            );
        }
        return show;
    }
}

export default Dashboard;
