import React, {Component} from 'react';
import {Redirect} from 'react-router-dom';

import {Breadcrumb, Button, Col, Divider, Form, Input, Row, message, Select, Radio} from 'antd';
import {NavLink} from "react-router-dom";
import Aux from "../../layouts/Aux";

import * as endpoint from '../../utilities/Endpoints';
import axios from '../../utilities/axios-spring-boot-api-instance';
import * as lang from '../../utilities/lang-kr';


/*const { Option } = Select;

function onChange(value) {
    console.log(`selected ${value}`);
}

function onBlur() {
    console.log('blur');
}

function onFocus() {
    console.log('focus');
}

function onSearch(val) {
    console.log('search:', val);
}*/

class AddPolicy extends Component {

    state = {
        confirmDirty: false,
        autoCompleteResult: [],
        redirect: false,
        allRules: [],

        // Selected rule to be saved.
        selectedItems: [],
    };

    componentDidMount() {
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

    handleChange = selectedItems => {
        this.setState({ selectedItems });
    };

    handleSubmit = e => {
        e.preventDefault();
        this.props.form.validateFieldsAndScroll((err, values) => {

            if (!err) {
                let hide = message.loading('저장중...');
                // console.log('Received values of form: ', values);
                const policy = JSON.stringify(values);
                console.log("Policy to save: ", policy);

                axios.post(endpoint.POLICY, values)
                    .then(res => {
                        setTimeout(hide, 0);
                        console.log(res);
                        message.success('성공적으로 저장되었습니다!');
                        this.setState({ redirect: true })
                    })
                    .catch(err => {
                        setTimeout(hide, 0);
                        console.log(err);
                        message.error('저장 실패!');
                    })
                ;
            }
        });
    };

    render() {
        if (!this.state.allRules){
            return "적어도 하나의 규칙이 필요합니다!"
        }

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

        return (
            <Aux>
                <Row>
                    <Col span={21}>
                        <Breadcrumb>
                            <Breadcrumb.Item>정책</Breadcrumb.Item>
                            <Breadcrumb.Item>
                                정책 추가
                            </Breadcrumb.Item>
                        </Breadcrumb>
                    </Col>
                    <Col span={3}>
                        <NavLink to={{
                            pathname: "/all-policy"
                        }}>
                            <span style={{paddingLeft: '10px'}}>
                                {/*Go to all rule page*/}
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
                            })(<Input placeholder={"Policy Name"}/>)}
                        </Form.Item>
                        <Form.Item label="목적파일명" >
                            {getFieldDecorator('destFileName', {
                                rules: [
                                    {
                                        required: false,
                                    },
                                ],
                            })(<Input/>)}
                        </Form.Item>
                        <Form.Item label="목적경로명" >
                            {getFieldDecorator('destPathName', {
                                rules: [
                                    {
                                        required: true,
                                        message: '목적경로명을 입력해주세요!',
                                    },
                                ],
                            })(<Input />)}
                        </Form.Item>
                        <Form.Item label="소스파일명" >
                            {getFieldDecorator('srcFileName', {
                                rules: [
                                    {
                                        required: false
                                    },
                                ],
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
                            })(<Input />)}
                        </Form.Item>
                        {/*<Form.Item label="사용여부 " >*/}
                        {/*    {getFieldDecorator('status', {*/}
                        {/*        rules: [{ required: false, message: '사용여부를 설정해주세요!' }],*/}
                        {/*    })(*/}
                        {/*        <Select*/}
                        {/*            showSearch*/}
                        {/*            style={{ width: 200 }}*/}
                        {/*            placeholder="Select a status"*/}
                        {/*            optionFilterProp="children"*/}
                        {/*            onChange={onChange}*/}
                        {/*            onFocus={onFocus}*/}
                        {/*            onBlur={onBlur}*/}
                        {/*            onSearch={onSearch}*/}
                        {/*            filterOption={(input, option) =>*/}
                        {/*                option.props.children.toLowerCase().indexOf(input.toLowerCase()) >= 0*/}
                        {/*            }*/}
                        {/*        >*/}
                        {/*            <Option value="true">True</Option>*/}
                        {/*            <Option value="false">False</Option>*/}
                        {/*        </Select>,*/}
                        {/*    )}*/}
                        {/*</Form.Item>*/}
                        <Form.Item label="사용여부 " >
                            {getFieldDecorator('status', {
                                rules: [{ required: true, message: '사용여부를 설정해주세요!' }],
                            })(
                                <Radio.Group name="status">
                                    <Radio style={{color: 'green'}} value={'Y'}>Active</Radio>
                                    <Radio style={{color: 'red'}} value={'N'}>Inactive</Radio>
                                </Radio.Group>
                            )}
                        </Form.Item>

                        <Form.Item label="관련된 규칙" >
                            {getFieldDecorator('ruleList', {
                                rules: [{ required: true, message: '관련된 규칙을 설정해주세요!' }],
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
}

const WrappedAddPolicyForm = Form.create({name: 'register'})(AddPolicy);

export default WrappedAddPolicyForm;
