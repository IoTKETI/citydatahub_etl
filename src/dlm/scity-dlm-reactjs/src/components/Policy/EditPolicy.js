import React, {Component} from 'react';
import {NavLink, Redirect} from 'react-router-dom';

import {Breadcrumb, Button, Col, Divider, Form, Input, message, Radio, Row, Select, Spin} from 'antd';
import Aux from "../../layouts/Aux";

import * as endpoint from '../../utilities/Endpoints';
import axios from '../../utilities/axios-spring-boot-api-instance';
import * as lang from '../../utilities/lang-kr';

class EditPolicy extends Component {

    state = {
        confirmDirty: false,
        autoCompleteResult: [],
        redirect: false,
        policy: null,
        // baseRuleList: [],

        allRules: [],

        // Selected base rules to be saved.
        selectedItems: [],

    };

    handleChange = selectedItems => {
        this.setState({ selectedItems });
    };

    handleSubmit = e => {
          /*
            "setupDate": "2019-12-10T16:59:04.804Z",
        }*/

        e.preventDefault();
        this.props.form.validateFieldsAndScroll((err, values) => {
            if (!err) {
                let hide = message.loading('저장중...');
                // console.log('Received values of form: ', values);
                const policy = JSON.stringify(values);
                console.log("Policy to edit: ", policy);
                axios.put(endpoint.POLICY + '/' + this.state.policy.policyId, values)
                    .then(res => {
                        setTimeout(hide, 0);
                        console.log(res);
                        message.success("\"" + values.policyName + '" 성공적으로 수정되었습니다!', 3);
                        this.setState({redirect: true});

                    })
                    .catch(err => {
                        console.log(err);
                        setTimeout(hide, 0);
                        message.error('수정 실패!');
                    })
                ;
            }
        });
    };

    /*compareToOldValue = (rule, value, callback) => {

        if (rule.field === 'destFileName') {
            if (value && value === this.state.policy.destFileName) {
                callback('Destination File Name must be changed!');
            } else {
                callback();
            }
        }
        else if (rule.field === 'srcFileName') {
            if (value && value === this.state.policy.srcFileName) {
                callback('Source File Name must be changed!');
            } else {
                callback();
            }
        }
    };*/

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
                    console.log(res);

                    const updatedPolicyRuleRId = res.data.data.policyRuleRelations.map(policyRule => {
                        return policyRule.ruleId;
                    });
                    this.setState({policy: res.data.data, selectedItems: updatedPolicyRuleRId});
                });

            // get all base rules
            axios.get(endpoint.RULE)
                .then(res => {
                    const updatedRule = res.data.data.map(rule => {
                        return {
                            key: rule.ruleId,
                            value: rule.ruleName
                        };
                    });

                    this.setState({allRules: updatedRule});
                });
        }
    }

    render() {
        const { selectedItems } = this.state;
        const filteredOptions = this.state.allRules.filter(o => {
            return !selectedItems.includes(o.value)
        });

        console.log(selectedItems);

        if (this.state.redirect) {
            return <Redirect to='/all-policy'/>;
        }

        const {getFieldDecorator} = this.props.form;
        const formItemLayout = {
            labelCol: {
                xs: {span: 6},
                sm: {span: 6},
            },
            wrapperCol: {
                xs: {span: 12},
                sm: {span: 12},
            },
        };
        const tailFormItemLayout = {
            labelCol: {span: 6},
            wrapperCol: {span: 12, offset: 6},
        };

        const policy = this.state.policy;
        if (policy) {
            return (
                <Aux>
                    <Row>
                        <Col span={21}>
                            <Breadcrumb>
                                <Breadcrumb.Item>정책</Breadcrumb.Item>
                                <Breadcrumb.Item>정책 수정</Breadcrumb.Item>
                            </Breadcrumb>
                        </Col>
                        <Col span={3}>

                            <NavLink to={{
                                pathname: "/all-policy"
                            }}>
                            <span style={{paddingLeft: '10px'}}>
                                {/*Go to all policy page*/}
                                <Button block icon="unordered-list" type="primary">정책 목록조회</Button>
                            </span>
                            </NavLink>
                        </Col>
                    </Row>

                    <Divider/>

                    <Row>
                        <span style={{color: "#f5222d"}}>*&nbsp;</span><span>{lang.requireField}</span>
                        <Form {...formItemLayout} onSubmit={this.handleSubmit}>
                            <Form.Item label="정책명">
                                {getFieldDecorator('policyName', {
                                    rules: [
                                        {
                                            required: true,
                                            message: '정책명을 입력해주세요!',
                                        },
                                    ],
                                    initialValue: policy.policyName
                                })(<Input placeholder={"Policy Name"}
                                          allowClear/>)}
                            </Form.Item>
                            <Form.Item label="목적파일명">
                                {getFieldDecorator('destFileName', {
                                    rules: [
                                        {
                                            required: false,
                                            message: '목적파일명을 입력해주세요!',
                                        },
                                        {
                                            // validator: this.compareToOldValue,
                                        },
                                    ],
                                    initialValue: policy.destFileName
                                })(<Input />)}
                            </Form.Item>
                            <Form.Item label="목적경로명">
                                {getFieldDecorator('destPathName', {
                                    rules: [
                                        {
                                            required: true,
                                            message: '목적경로명을 입력해주세요!',
                                        },
                                    ],
                                    initialValue: policy.destPathName
                                })(<Input />)}
                            </Form.Item>
                            <Form.Item label="소스파일명" >
                                {getFieldDecorator('srcFileName', {
                                    rules: [
                                        {
                                            required: false,
                                            message: '소스파일명을 입력해주세요!',
                                        },
                                        {
                                            // validator: this.compareToOldValue,
                                        },
                                    ],
                                    initialValue: policy.srcFileName
                                })(<Input/>)}
                            </Form.Item>
                            <Form.Item label="소스경로명" >
                                {getFieldDecorator('srcPathName', {
                                    rules: [
                                        {
                                            required: true,
                                            message: '소스경로명을 입력해주세요!',
                                        },
                                    ],
                                    initialValue: policy.srcPathName
                                })(<Input />)}
                            </Form.Item>

                            <Form.Item label="사용여부 " >
                                {getFieldDecorator('status', {
                                    rules: [{ required: true, message: '사용여부를 설정해주세요!' }],
                                    initialValue: policy.status
                                })(
                                    <Radio.Group name="status">
                                        <Radio style={{color: 'green'}} value={'Y'}>Active</Radio>
                                        <Radio style={{color: 'red'}} value={'N'}>Inactive</Radio>
                                    </Radio.Group>
                                )}
                            </Form.Item>


                            <Form.Item label="관련된 규칙" >
                                {getFieldDecorator('ruleList', {
                                    rules: [{ required: true, message: '관련된 규칙을 선택해주세요!' }],
                                    initialValue: selectedItems
                                })(
                                    <Select
                                        mode="multiple"
                                        placeholder="Please select Rule"
                                        // value={selectedItems}
                                        onChange={this.handleChange}
                                        style={{ width: '100%' }}>

                                        {filteredOptions.map(item => (
                                            /*Use below commented code instead of above to select base rule without removing a selected base rule*/
                                            // {this.state.allBaseRuleNames.map(item => (
                                            <Select.Option key={item.key} value={item.key}>
                                                {item.value}
                                            </Select.Option>
                                        ))}
                                    </Select>
                                )}
                            </Form.Item>

                            <Form.Item {...tailFormItemLayout}>
                                <Row>
                                    <Col span={8}>
                                        <Button type="primary" block htmlType="submit" icon={"edit"}>
                                            저장
                                        </Button>
                                    </Col>
                                </Row>
                            </Form.Item>
                        </Form>

                    </Row>
                </Aux>
            );
        }
        return (
            <div style={{textAlign: 'center'}}>
                <Spin size={"large"}/>
            </div>
        );

    }
}

const WrappedEditPolicyForm = Form.create({name: 'register'})(EditPolicy);

export default WrappedEditPolicyForm;
