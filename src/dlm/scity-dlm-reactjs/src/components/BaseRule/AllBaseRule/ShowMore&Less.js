import React, {Component} from 'react';
// import Aux from '../../../layouts/Aux';
// import {Breadcrumb, Button, Col, Divider, Modal, Row, Table, Tag} from 'antd';

// import * as tableColumns from '../../../TableColumn';
import * as endpoint from '../../../utilities/Endpoints';
import axios from '../../../utilities/axios-spring-boot-api-instance'
import {NavLink} from "react-router-dom";
import {Button} from "antd";

class AllBaseRule extends Component {

    constructor() {
        super();
        this.state = {
            allBaseRule: [],
            modal1Visible: false,
            showAll: false,

        };
    }

    showMore = () => this.setState({showAll: true});
    showLess = () => this.setState({showAll: false});

    componentDidMount() {
        axios.get(endpoint.BASE_RULE)
            .then(res => {
                console.log(res);
                // eslint-disable-next-line array-callback-return
                let updatedBaseRule = res.data.data.map(baseRule => {
                    return {
                        id: baseRule.baseRuleId,
                        key: baseRule.baseRuleId,
                        dlft_delete_cycle: baseRule.defaultDeleteCycle,
                        dlft_move_cycle: baseRule.defaultMoveCycle,
                        base_rule_nm: baseRule.baseRuleName,
                        desc: baseRule.description,
                        memo: baseRule.memo,
                        mod_dtm: baseRule.modifiedDate,
                        modr_id: baseRule.modifiedUser,
                        reg_dtm: baseRule.createdDate,
                        regr_id: baseRule.createdUser
                    };
                });
                this.setState({allBaseRule: updatedBaseRule});
            })
    }

    render() {
        const content = 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has' +
            ' been the industry\'s standard dummy text ever since the 1500s, when an unknown printer took a galley of ' +
            'type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the ' +
            'leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the ' +
            'release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software' +
            ' like Aldus PageMaker including versions of Lorem Ipsum.';
        const limit = 250;
        const showAll = this.state.showAll;

        if (content.length <= limit) {
            return <div>{content}</div>
        }

        if (showAll) {
            return (
                <div>
                    {content}
                    <NavLink to={{
                        // pathname: '/rule/edit/' + record.id,
                        // hash: '#submit',
                        // search: '?id=' + record.componentId
                    }}>
                        <Button onClick={this.showLess} size={"small"} type={"primary"} icon={"edit"}>Read Less</Button>
                    </NavLink>
                </div>
            )
        }

        const toShow = content.substring(0, limit) + "...";
        return (
            <div>
                {toShow}
                <span onClick={this.showMore}>Read more</span>
            </div>
        )

    }


}

export default AllBaseRule;
