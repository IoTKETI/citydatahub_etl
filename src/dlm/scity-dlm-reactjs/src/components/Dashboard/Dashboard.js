import React, {Component} from 'react';
import Aux from '../../layouts/Aux';
import {Row, Col, Card, Statistic, Spin} from 'antd';
import * as endpoint from '../../utilities/Endpoints';
import axios from '../../utilities/axios-spring-boot-api-instance'

class Dashboard extends Component {

    constructor() {
        super();
        this.state = {
            totalBaseRule: 0,
            totalRule: 0,
            totalPolicy: 0,
            status: false
        };
    }

    componentDidMount() {
        axios.get(endpoint.BASE_RULE)
            .then(res => {
                console.log(res);
                this.setState({totalBaseRule: res.data.data.length, status: true});
            });
        axios.get(endpoint.RULE)
            .then(res => {
                console.log(res);
                this.setState({totalRule: res.data.data.length, status: true});
            });
        axios.get(endpoint.POLICY)
            .then(res => {
                console.log(res);
                this.setState({totalPolicy: res.data.data.length, status: true});
            })
    }


    render() {
        if (this.state.status) {
            return (
                <Aux>
                    <Row gutter={16}>
                        <Col span={8}>
                            <Card>
                                <Statistic
                                    title="Total Base Rules"
                                    value={this.state.totalBaseRule}
                                    valueStyle={{ color: '#3f8600' }}
                                />
                            </Card>
                        </Col>
                        <Col span={8}>
                            <Card>
                                <Statistic
                                    title="Total Rules"
                                    value={this.state.totalRule}
                                    valueStyle={{ color: '#cf1322' }}
                                />
                            </Card>
                        </Col>
                        <Col span={8}>
                            <Card>
                                <Statistic
                                    title="Total Policies"
                                    value={this.state.totalPolicy}
                                    valueStyle={{ color: 'rgb(207, 159, 93)' }}
                                />
                            </Card>
                        </Col>
                    </Row>
                </Aux>
            )
        }

        return <Spin tip={"Loading..."}/>
    }


}

export default Dashboard;
