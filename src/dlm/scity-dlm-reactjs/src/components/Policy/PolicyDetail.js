import React, {Component} from 'react';
import {NavLink} from 'react-router-dom';

import * as endpoint from '../../utilities/Endpoints';
import axios from '../../utilities/axios-spring-boot-api-instance'

import {Breadcrumb, Button, Col, Divider, Form, Input, Row, Spin, Tag} from 'antd';
import Aux from "../../layouts/Aux";
import Moment from "react-moment";


class PolicyDetail extends Component {

    state = {
        confirmDirty: false,
        autoCompleteResult: [],
        policy: null,

    };

    componentDidMount(prevProps, prevState, snapshot) {
        console.log(this.props.history.location.pathname);
        console.log(this.props);

        if (this.props.match.params.id) {
            axios({
                method: 'get',
                url: endpoint.POLICY + '/' + this.props.match.params.id,
                cors: true,
                withCredentials: true
            })
                .then(res => {
                    console.log(res.data.data);
                    this.setState({policy: res.data.data});
                });
        }
    }

    render() {
        const formItemLayout = {
            labelCol: {
                xs: {span: 6},
                sm: {span: 6},
            },
            wrapperCol: {
                xs: {span: 16},
                sm: {span: 16},
            },
        };
        const policy = this.state.policy;

        if (policy) {
            let policyRuleRelation = [];

            if (policy.policyRuleRelations.length > 0) {
                console.log('juy');
                policyRuleRelation = policy.policyRuleRelations.map(relation => {
                    return <Tag color={'green'} key={relation.ruleId}>{relation.ruleName}</Tag>;
                });
            }

            return (
                <Aux>
                    <Row>
                        <Col span={21}>
                            <Breadcrumb>
                                <Breadcrumb.Item>정책</Breadcrumb.Item>
                                <Breadcrumb.Item>정책 상세조회</Breadcrumb.Item>
                                <Breadcrumb.Item>{policy.policyId}</Breadcrumb.Item>

                                <Breadcrumb.Item>

                                    <NavLink to={{
                                        pathname: "/policy/edit/" + policy.policyId
                                    }}>
                                        <span style={{paddingLeft: '10px'}}>
                                            {/*Edit current Policy*/}
                                            <Button type={"danger"} size={"small"} icon={'edit'}>수정</Button>
                                        </span>
                                    </NavLink>
                                </Breadcrumb.Item>

                            </Breadcrumb>
                        </Col>
                        <Col span={3}>

                            <NavLink to={{
                                pathname: "/all-policy"
                            }}>
                            <span style={{paddingLeft: '10px'}}>
                                {/*Go to all policy page*/}
                                <Button
                                    block
                                    icon="unordered-list"
                                    type="primary"
                                >
                                    정책 목록조회
                                </Button>
                            </span>
                            </NavLink>

                        </Col>
                    </Row>

                    <Divider/>

                    <Row>
                        <Form {...formItemLayout}>
                            <Col span={12}>
                                <Form.Item label="정책ID">
                                    <Input style={{width: '30%'}} placeholder={"Policy ID"}
                                           defaultValue={policy.policyId} readOnly/>
                                </Form.Item>
                            </Col>
                            <Col span={12}>
                                <Form.Item label="정책명">
                                    <Input placeholder={"Policy Name"} defaultValue={policy.policyName} readOnly/>
                                </Form.Item>
                            </Col>
                            <Col span={12}>
                                <Form.Item label="소스경로명" >
                                    <Input readOnly defaultValue={policy.srcPathName}/>
                                </Form.Item>
                            </Col>
                            <Col span={12}>
                                <Form.Item label="소스파일명" >
                                    <Input readOnly defaultValue={policy.srcFileName}/>
                                </Form.Item>
                            </Col>
                            <Col span={12}>
                                <Form.Item label="목적경로명" >
                                    <Input readOnly defaultValue={policy.destPathName}/>
                                </Form.Item>
                            </Col>
                            <Col span={12}>
                                <Form.Item label="목적파일명" >
                                    <Input readOnly defaultValue={policy.destFileName}/>
                                </Form.Item>
                            </Col>
                            <Col span={12}>
                                <Form.Item label="등록자ID" >
                                    <Input readOnly defaultValue={policy.createdUser}/>
                                </Form.Item>
                            </Col>
                            <Col span={12}>
                                <Form.Item label="수정자ID" >
                                    <Input readOnly defaultValue={policy.modifiedUser}/>
                                </Form.Item>
                            </Col>
                            <Col span={12}>
                                <Form.Item label="등록일시" >
                                    <Tag style={{fontSize: "small"}} color="blue"><Moment>{policy.createdDate}</Moment></Tag>
                                </Form.Item>
                            </Col>
                            <Col span={12}>
                                <Form.Item label="수정일시" >
                                    <Tag style={{fontSize: "small"}} color="blue"><Moment>{policy.modifiedDate}</Moment></Tag>

                                </Form.Item>
                            </Col>
                            <Col span={12}>
                                <Form.Item label="정책설정일시" >
                                    <Tag style={{fontSize: "small"}}
                                         color="blue"><Moment>{policy.setupDate}</Moment></Tag>
                                </Form.Item>
                            </Col>
                            <Col span={12}>
                                <Form.Item label="사용여부" >
                                    {policy.status.toUpperCase() === 'Y' ? <Tag color={'green'}>ACTIVE</Tag> : <Tag color={'volcano'}>INACTIVE</Tag>}
                                </Form.Item>
                            </Col>

                            <Row>
                                {policyRuleRelation.length > 0 ?

                                    <Col span={12}>
                                        <Form.Item label="관련된 규칙">
                                            {policyRuleRelation}
                                        </Form.Item>
                                    </Col> : ''}
                            </Row>

                        </Form>
                    </Row>
                </Aux>
            );
        }
        return (
            <div style={{textAlign: 'center'}}>
                <Spin/>
            </div>
        );
    }
}

export default PolicyDetail;
