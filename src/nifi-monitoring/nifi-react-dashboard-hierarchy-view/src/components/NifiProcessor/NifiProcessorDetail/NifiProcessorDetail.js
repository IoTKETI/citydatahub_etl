import React, {Component} from 'react';

import axios from '../../../axios-spring-boot-api-instance';
import Aux from "../../../hoc/Aux/Aux";
import {Badge, Button, Col, Collapse, Divider, Form, Icon, Input, Row, Spin, Tag} from "antd";
import * as endpoint from "../../../Endpoints";
import {NavLink} from "react-router-dom";
import {faExclamationTriangle, faPlay, faStop} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

const {Panel} = Collapse;
const {TextArea} = Input;

const customPanelStyle = {
    background: '#f7f7f7',
    borderRadius: 4,
    marginBottom: 5,
    border: 0,
    overflow: 'hidden',
};

/*Get Processor Component in Detail*/
class NifiProcessorDetail extends Component {
    constructor(props) {
        super(props);
        this.state = {
            formLayout: 'horizontal',
            processor: {},
        };
    }

    componentDidMount(prevProps, prevState, snapshot) {

        if (this.props.match.params.id) {
            axios({
                method: 'get',
                url: endpoint.PROCESSOR + '/' + this.props.match.params.id,
                cors: true,
                withCredentials: true
            })
                .then(res => {
                    if (res.data.status)
                        this.setState({processor: res.data.data});

                    console.log(this.state.processor)
                });
        }
    }

    render() {
        const {formLayout} = this.state;
        const formItemLayout =
            formLayout === 'horizontal'
                ? {
                    labelCol: {span: 6},
                    wrapperCol: {span: 14},
                }
                : null;

        let processor_show = <Spin tip="Loading...">Detail processor</Spin>;

        if (this.props.id) {
            processor_show = <Spin tip="Loading...">Detail processor</Spin>;
        }

        if (this.state.processor.status) {
            let icon = null;
            let runningStatus = this.state.processor.status.aggregateSnapshot.runStatus;
            if (runningStatus.toLowerCase() === 'running')
                icon = <FontAwesomeIcon color='rgb(125, 199, 160)' icon={faPlay} size="sm"/>;
            else if (runningStatus.toLowerCase() === 'stopped')
                icon = <FontAwesomeIcon color='rgb(209, 134, 134)' icon={faStop} size="sm"/>;
            else
                icon = <FontAwesomeIcon color='#cf9f5d' icon={faExclamationTriangle} size="xs"/>;

            let bulletinsToShow = null;
            if (this.state.processor.bulletins.length) {

                const bulletins = this.state.processor.bulletins.map(bulletin => {
                    return (
                        <Panel header={bulletin.bulletin.category + ' - ' + bulletin.bulletin.level} key={bulletin.id}
                               style={customPanelStyle}>
                            <Row>
                                <Col span={12} style={{textAlign: 'right'}}>
                                    <TextArea value={bulletin.bulletin.message} placeholder="Bulletin log message"
                                              autosize/>
                                </Col>
                            </Row>
                        </Panel>
                    );
                });

                bulletinsToShow = (
                    <Aux>
                        <Divider/>

                        <Badge count={this.state.processor.bulletins.length}>
                            <Tag color='red'><Icon type="file-exclamation" theme="filled"/> Bulletin</Tag>
                        </Badge>

                        <Collapse
                            style={{marginTop: '15px'}}
                            bordered={false}
                            // defaultActiveKey={['1']}
                            expandIcon={({isActive}) => <Icon type="caret-right" rotate={isActive ? 90 : 0}/>}
                        >
                            {bulletins}

                        </Collapse>
                    </Aux>

                );
            }

            processor_show = (
                <Aux>
                    <div style={{padding: 24, background: '#fff', minHeight: 360}}>
                        <Form layout={formLayout}>

                            <Row>
                                <Col span={12} style={{textAlign: 'right'}}>
                                    <Form.Item label="Processor Name" {...formItemLayout}>
                                        <Input disabled={false} value={this.state.processor.component.name}
                                               placeholder="Processor Name"/>
                                    </Form.Item>
                                </Col>
                                <Col span={12} style={{textAlign: 'right'}}>
                                    <Form.Item label="Parent Process Group" {...formItemLayout}>
                                        <Input placeholder="Parent Process Group" allowClear={false}
                                               value={this.state.processor.parentProcessGroup.component.name}/>
                                    </Form.Item>
                                </Col>
                            </Row>

                            <Row>
                                <Col span={12} style={{textAlign: 'right'}}>
                                    <Form.Item label="Run Status" {...formItemLayout}>
                                        <Input disabled={false} value={runningStatus}
                                               placeholder="Run Status"
                                               prefix={icon}
                                        />
                                    </Form.Item>
                                </Col>
                                <Col span={12} style={{textAlign: 'right'}}>
                                    <Form.Item label="Execute on node" {...formItemLayout}>
                                        <Input disabled={false}
                                               value={this.state.processor.status.aggregateSnapshot.executionNode}
                                               placeholder="Execute on node"
                                        />
                                    </Form.Item>
                                </Col>
                            </Row>

                            <Row>
                                <Col span={12} style={{textAlign: 'right'}}>
                                    <Form.Item label="Input / Size" {...formItemLayout}>
                                        <Input disabled={false}
                                               value={this.state.processor.status.aggregateSnapshot.input}
                                               placeholder="Input"
                                        />
                                    </Form.Item>
                                </Col>
                                <Col span={12} style={{textAlign: 'right'}}>
                                    <Form.Item label="Output / Size" {...formItemLayout}>
                                        <Input disabled={false}
                                               value={this.state.processor.status.aggregateSnapshot.output}
                                               placeholder="Output"
                                        />
                                    </Form.Item>
                                </Col>
                            </Row>

                            {/*<Row>*/}
                            {/*    <Col span={12} style={{textAlign: 'right'}}>*/}
                            {/*        <Form.Item label="Flow Files Queued" {...formItemLayout}>*/}
                            {/*            <Input disabled={false} defaultValue={this.state.processor.status.aggregateSnapshot.queued}*/}
                            {/*                   placeholder="Queued"/>*/}
                            {/*        </Form.Item>*/}
                            {/*    </Col>*/}
                            {/*    <Col span={12} style={{textAlign: 'right'}}>*/}
                            {/*        <Form.Item label="Flow Files Input" {...formItemLayout}>*/}
                            {/*            <Input disabled={false} value={this.state.processor.status.aggregateSnapshot.input}*/}
                            {/*                   placeholder="Destination Name"*/}
                            {/*            />*/}

                            {/*        </Form.Item>*/}
                            {/*    </Col>*/}
                            {/*</Row>*/}


                            {bulletinsToShow}


                            <Divider/>

                            <NavLink to={{pathname: "/components"}}>
                                <Button type="danger"><Icon type="caret-left" theme="filled"/> Back</Button>
                            </NavLink>

                        </Form>
                    </div>
                </Aux>);
        }
        return processor_show;
    }
}

export default NifiProcessorDetail;
