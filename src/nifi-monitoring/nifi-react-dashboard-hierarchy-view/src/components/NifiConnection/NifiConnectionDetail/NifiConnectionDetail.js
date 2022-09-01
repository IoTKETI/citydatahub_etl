import React, {Component} from 'react';

import axios from '../../../axios-spring-boot-api-instance';
import Aux from "../../../hoc/Aux/Aux";
import {Button, Col, Divider, Form, Icon, Input, Row, Spin} from "antd";
import * as endpoint from "../../../Endpoints";
import {NavLink} from "react-router-dom";


/*Get Connection Component in Detail*/
class NifiConnectionDetail extends Component {
    constructor(props) {
        super(props);
        this.state = {
            formLayout: 'horizontal',
            connection: {},
        };
    }

    componentDidMount(prevProps, prevState, snapshot) {
        console.log(this.props.history.location.pathname);
        console.log(this.props);

        if (this.props.match.params.id) {
            axios({
                method: 'get',
                url: endpoint.CONNECTION + '/' + this.props.match.params.id,
                cors: true,
                withCredentials: true
            })
                .then(res => {
                    if (res.data.status)
                        this.setState({connection: res.data.data});
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

        let connection_show = <Spin tip="Loading...">Detail connection</Spin>;

        if (this.props.id) {
            connection_show = <Spin tip="Loading...">Detail connection</Spin>;
        }

        if (this.state.connection.status) {
            console.log(this.state.connection);

            connection_show = (
                <Aux>
                    <div style={{padding: 24, background: '#fff', minHeight: 360}}>
                        <Form layout={formLayout}>

                            <Row>
                                <Col span={12} style={{textAlign: 'right'}}>
                                    <Form.Item label="Connection Name" {...formItemLayout}>
                                        <Input disabled={false} value={this.state.connection.status.name}
                                               placeholder="Connection Name"/>
                                    </Form.Item>
                                </Col>
                                <Col span={12} style={{textAlign: 'right'}}>
                                    <Form.Item label="Parent Process Group" {...formItemLayout}>
                                        <Input placeholder="Parent Process Group" allowClear={false}
                                               value={this.state.connection.parentProcessGroup.component.name}/>
                                    </Form.Item>
                                </Col>
                            </Row>

                            <Row>
                                <Col span={12} style={{textAlign: 'right'}}>
                                    <Form.Item label="Source Type" {...formItemLayout}>
                                        <Input disabled={false} value={this.state.connection.sourceType}
                                               placeholder="Source Type"/>
                                    </Form.Item>
                                </Col>
                                <Col span={12} style={{textAlign: 'right'}}>
                                    <Form.Item label="Destination Type" {...formItemLayout}>
                                        <Input disabled={false} value={this.state.connection.destinationType}
                                               placeholder="Destination Type"/>
                                    </Form.Item>
                                </Col>
                            </Row>

                            <Row>
                                <Col span={12} style={{textAlign: 'right'}}>
                                    <Form.Item label="Source Name" {...formItemLayout}>
                                        <Input disabled={false} value={this.state.connection.component.source.name}
                                               placeholder="Source Name"
                                               suffix={
                                                   <Button type="primary" size="small"><Icon type="eye"
                                                                                             theme="filled"/></Button>
                                               }
                                        />
                                    </Form.Item>
                                </Col>
                                <Col span={12} style={{textAlign: 'right'}}>
                                    <Form.Item label="Destination Name" {...formItemLayout}>
                                        <Input disabled={false} value={this.state.connection.component.destination.name}
                                               placeholder="Destination Name"
                                               suffix={
                                                   <Button type="primary" size="small"><Icon type="eye"
                                                                                             theme="filled"/></Button>
                                               }
                                        />
                                    </Form.Item>
                                </Col>
                            </Row>

                            <Row>
                                <Col span={12} style={{textAlign: 'right'}}>
                                    <Form.Item label="Flow Files Queued" {...formItemLayout}>
                                        <Input disabled={false}
                                               value={this.state.connection.status.aggregateSnapshot.queued}
                                               placeholder="Queued"/>
                                    </Form.Item>
                                </Col>
                                <Col span={12} style={{textAlign: 'right'}}>
                                    <Form.Item label="Flow Files Input" {...formItemLayout}>
                                        <Input disabled={false}
                                               value={this.state.connection.status.aggregateSnapshot.input}
                                               placeholder="Destination Name"
                                        />

                                    </Form.Item>
                                </Col>
                            </Row>

                            <Divider/>

                            <NavLink to={{pathname: "/components"}}>
                                <Button type="danger"><Icon type="caret-left" theme="filled"/> Back</Button>
                            </NavLink>

                        </Form>
                    </div>
                </Aux>);
        }
        return connection_show;
    }
}

export default NifiConnectionDetail;
