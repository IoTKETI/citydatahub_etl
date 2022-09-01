import React, {Component} from 'react';
import {NavLink, Redirect} from 'react-router-dom';

import {Breadcrumb, Button, Col, Divider, Form, Input, InputNumber, message, Row, Select, Spin} from 'antd';
import Aux from "../../layouts/Aux";

import * as endpoint from '../../utilities/Endpoints';
import axios from '../../utilities/axios-spring-boot-api-instance';
import * as lang from '../../utilities/lang-kr';

const {TextArea} = Input;

class EditRule extends Component {

    state = {
        confirmDirty: false,
        autoCompleteResult: [],
        redirect: false,
        rule: null,
        // baseRuleList: [],

        allBaseRules: [],

        // Selected base rules to be saved.
        selectedItems: [],

    };

    handleSubmit = e => {

        // {"ruleName":"fsdfsdf","moveCycle":4,"deleteCycle":44,"baseRuleList":[4,6],"description":"fsdf","memo":"sdfsf"}


        e.preventDefault();
        this.props.form.validateFieldsAndScroll((err, values) => {

            /*Start checking duplicate base name*/

            /*End checking duplicate base name*/


            if (!err) {
                let hide = message.loading('저장중...');
                // console.log('Received values of form: ', values);
                const rule = JSON.stringify(values);
                console.log("Rule to edit: ", rule);
                axios.put(endpoint.RULE + '/' + this.state.rule.ruleId, values)
                    .then(res => {
                        setTimeout(hide, 0);
                        console.log(res);
                        message.success("\"" + values.ruleName + '" 성공적으로 수정되었습니다!', 3);
                        this.setState({redirect: true});

                    })
                    .catch(err => {
                        setTimeout(hide, 0);
                        console.log(err);
                        message.error('수정 실패!');
                    })
                ;
            }
        });
    };

    /* handleConfirmBlur = e => {
         const {value} = e.target;
         this.setState({confirmDirty: this.state.confirmDirty || !!value});
     };*/

    compareToFirstPassword = (rule, value, callback) => {
        const {form} = this.props;
        console.log(value);
        console.log(form.getFieldValue('moveCycle'));
        if (value && value <= form.getFieldValue('moveCycle')) {
            callback('삭제주기는 이관주기보다 짧을 수 없습니다!');
        } else {
            callback();
        }
    };
    validateToNextPassword = (rule, value, callback) => {
        // console.log(rule);
        // console.log(value);
        const {form} = this.props;
        if (value && this.state.confirmDirty) {
            form.validateFields(['deleteCycle'], {force: true});
        }
        callback();
    };

    componentDidMount(prevProps, prevState, snapshot) {
        console.log(this.props.history.location.pathname);
        console.log(this.props);

        if (this.props.match.params.id) {
            axios({
                method: 'get',
                url: endpoint.RULE + '/' + this.props.match.params.id,
                cors: true,
                withCredentials: true
            })
                .then(res => {
                    console.log(res);

                    const updatedRuleBaseRuleRId = res.data.data.ruleBaseRuleRelations.map(ruleBaseRule => {
                        return ruleBaseRule.baseRuleId;
                    });
                    this.setState({rule: res.data.data, selectedItems: updatedRuleBaseRuleRId});
                });

            // get all base rules
            axios.get(endpoint.BASE_RULE)
                .then(res => {
                    const updatedBaseRule = res.data.data.map(baseRule => {
                        return {
                            key: baseRule.baseRuleId,
                            value: baseRule.baseRuleName
                        };
                    });

                    this.setState({allBaseRules: updatedBaseRule});
                });
        }
    }


    render() {
        const { selectedItems } = this.state;
        const filteredOptions = this.state.allBaseRules.filter(o => {
            return !selectedItems.includes(o.value)
        });

        console.log(selectedItems);

        if (this.state.redirect) {
            return <Redirect to='/all-rule'/>;
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

        const rule = this.state.rule;
        if (rule) {
            return (
                <Aux>
                    <Row>
                        <Col span={21}>
                            <Breadcrumb>
                                <Breadcrumb.Item>규칙</Breadcrumb.Item>
                                <Breadcrumb.Item>규칙 수정</Breadcrumb.Item>
                            </Breadcrumb>
                        </Col>
                        <Col span={3}>

                            <NavLink to={{
                                pathname: "/all-rule"
                            }}>
                            <span style={{paddingLeft: '10px'}}>
                                {/*Go to all rule page*/}
                                <Button block icon="unordered-list" type="primary">규칙 목록조회</Button>
                            </span>
                            </NavLink>
                        </Col>
                    </Row>

                    <Divider/>

                    <Row>
                        <span style={{color: "#f5222d"}}>*&nbsp;</span><span>{lang.requireField}</span>
                        <Form {...formItemLayout} onSubmit={this.handleSubmit}>

                            <Form.Item label="규칙명">
                                {getFieldDecorator('ruleName', {
                                    rules: [
                                        {
                                            required: true,
                                            message: '규칙명을 입력해주세요!',
                                        },
                                    ],
                                    initialValue: rule.ruleName
                                })(<Input placeholder={"Rule Name"}
                                          allowClear/>)}
                            </Form.Item>
                            <Form.Item label="이관주기" hasFeedback>
                                {getFieldDecorator('moveCycle', {
                                    rules: [
                                        {
                                            required: true,
                                            message: '이관주기를 설정해주세요!',
                                        },
                                        {
                                            validator: this.validateToNextPassword,
                                        },
                                    ],
                                    initialValue: rule.moveCycle
                                })(<InputNumber min={1}/>)}
                            </Form.Item>
                            <Form.Item label="삭제주기" hasFeedback>
                                {getFieldDecorator('deleteCycle', {
                                    rules: [
                                        {
                                            required: true,
                                            message: '삭제주기를 설정해주세요!',
                                        },
                                        {
                                            validator: this.compareToFirstPassword,
                                        },
                                    ],
                                    initialValue: rule.deleteCycle
                                })(<InputNumber min={1}/>)}
                            </Form.Item>

                            <Form.Item label="관련된 기본규칙" hasFeedback>
                                {getFieldDecorator('baseRuleList', {
                                    rules: [{ required: true, message: '기본규칙을 선택해주세요!' }],
                                    initialValue: selectedItems
                                })(
                                    <Select
                                        mode="multiple"
                                        placeholder="Please select Base Rule"
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

                            <Form.Item label="설명">
                                {getFieldDecorator('description', {
                                    rules: [{required: false}],
                                    initialValue: rule.description
                                })(
                                    <TextArea
                                        placeholder="description"
                                        autoSize={{minRows: 5, maxRows: 20}}
                                        allowClear
                                    />
                                )}
                            </Form.Item>
                            <Form.Item label="메모">
                                {getFieldDecorator('memo', {
                                    rules: [{required: false}],
                                    initialValue: rule.memo
                                })(
                                    <TextArea
                                        placeholder="Memo"
                                        autoSize={{minRows: 5, maxRows: 20}}
                                        allowClear
                                    />
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

const WrappedEditRuleForm = Form.create({name: 'register'})(EditRule);

export default WrappedEditRuleForm;
