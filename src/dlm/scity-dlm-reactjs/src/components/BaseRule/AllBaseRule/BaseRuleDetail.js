import React, {Component} from 'react';
import {NavLink} from 'react-router-dom';

import * as endpoint from '../../../utilities/Endpoints';
import axios from '../../../utilities/axios-spring-boot-api-instance';
import Moment from 'react-moment';

import {Breadcrumb, Button, Col, Divider, Form, Input, InputNumber, Row, Spin, Tag} from 'antd';
import Aux from "../../../layouts/Aux";

const {TextArea} = Input;

class BaseRuleDetail extends Component {

    state = {
        confirmDirty: false,
        autoCompleteResult: [],
        baseRule: null,

    };

    componentDidMount(prevProps, prevState, snapshot) {
        console.log(this.props.history.location.pathname);
        console.log(this.props);

        if (this.props.match.params.id) {
            axios({
                method: 'get',
                url: endpoint.BASE_RULE + '/' + this.props.match.params.id,
                cors: true,
                withCredentials: true
            })
                .then(res => {
                    console.log(res.data.data);
                    this.setState({baseRule: res.data.data});
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

        const baseRule = this.state.baseRule;

        if (baseRule) {

            return (
                <Aux>
                    <Row>
                        <Col span={21}>
                            <Breadcrumb>
                                <Breadcrumb.Item>기본규칙</Breadcrumb.Item>
                                <Breadcrumb.Item>기본규칙 상세</Breadcrumb.Item>
                                <Breadcrumb.Item>{baseRule.baseRuleId}</Breadcrumb.Item>
                                <Breadcrumb.Item>

                                    <NavLink to={{
                                        pathname: "/base-rule/edit/" + baseRule.baseRuleId
                                    }}>
                                        <span style={{paddingLeft: '10px'}}>
                                            {/*Edit current Base Rule*/}
                                            <Button type={"danger"} size={"small"} icon={'edit'} >수정</Button>
                                        </span>
                                    </NavLink>
                                </Breadcrumb.Item>
                            </Breadcrumb>
                        </Col>
                        <Col span={3}>

                            <NavLink to={{
                                pathname: "/all-base-rule"
                            }}>
                                <span style={{paddingLeft: '10px'}}>
                                    {/*Go to all base rule page*/}
                                    <Button
                                        block
                                        icon="unordered-list"
                                        type="primary"
                                    >
                                        기본규칙 목록조회
                                    </Button>
                                </span>
                            </NavLink>

                        </Col>
                    </Row>

                    <Divider/>

                    <Row>
                        <Form {...formItemLayout}>
                            <Col span={12}>
                                <Form.Item label="기본규칙ID">
                                    <Input style={{width: '30%'}} placeholder={"Base Rule ID"} defaultValue={baseRule.baseRuleId} readOnly/>
                                </Form.Item>
                            </Col>

                            <Col span={12}>
                                <Form.Item label="기본규칙명">
                                    <Input placeholder={"Base Rule Name"} defaultValue={baseRule.baseRuleName} readOnly/>
                                </Form.Item>
                            </Col>

                            <Col span={12}>
                                <Form.Item label="디폴트이관주기" hasFeedback>
                                    <InputNumber style={{width: '30%'}} min={0} defaultValue={baseRule.defaultMoveCycle} readOnly/>
                                </Form.Item>
                            </Col>

                            <Col span={12}>
                                <Form.Item label="디폴트삭제주기" hasFeedback>
                                    <InputNumber style={{width: '30%'}} min={0} defaultValue={baseRule.defaultDeleteCycle} readOnly/>
                                </Form.Item>
                            </Col>

                            <Col span={12}>
                                <Form.Item label="등록자ID" hasFeedback>
                                    <Input min={0} defaultValue={baseRule.createdUser} readOnly/>
                                </Form.Item>
                            </Col>

                            <Col span={12}>
                                <Form.Item label="수정자ID" hasFeedback>
                                    <Input min={0} defaultValue={baseRule.modifiedUser} readOnly/>
                                </Form.Item>
                            </Col>

                            <Col span={12}>
                                <Form.Item label="등록일시" hasFeedback>
                                    <Tag style={{fontSize: "small"}} color="blue"><Moment>{baseRule.createdDate}</Moment></Tag>
                                </Form.Item>
                            </Col>

                            <Col span={12}>
                                <Form.Item label="수정일시" hasFeedback>
                                    <Tag style={{fontSize: "small"}} color="blue"><Moment>{baseRule.modifiedDate}</Moment></Tag>

                                </Form.Item>
                            </Col>

                            <Col span={12}>
                                <Form.Item label="설명">
                                    <TextArea
                                        defaultValue={baseRule.description}
                                        readOnly
                                        placeholder="description"
                                        autoSize
                                    />
                                </Form.Item>
                            </Col>

                            <Col span={12}>
                                <Form.Item label="메모">
                                    <TextArea
                                        defaultValue={baseRule.memo}
                                        readOnly
                                        placeholder="Memo"
                                        autoSize
                                    />
                                </Form.Item>
                            </Col>
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

export default BaseRuleDetail;
